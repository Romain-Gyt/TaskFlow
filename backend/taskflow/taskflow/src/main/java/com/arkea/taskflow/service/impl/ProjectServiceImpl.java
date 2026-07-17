package com.arkea.taskflow.service.impl;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.dto.ProjectTaskRequest;
import com.arkea.taskflow.mapper.ProjectMapper;
import com.arkea.taskflow.mapper.TaskMapper;
import com.arkea.taskflow.model.Project;
import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.repository.ProjectRepository;
import com.arkea.taskflow.service.ProjectService;
import com.arkea.taskflow.engine.RsePointsEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final RsePointsEngine rsePointsEngine;

    public ProjectServiceImpl(ProjectMapper projectMapper,
                              ProjectRepository projectRepository,
                              RsePointsEngine rsePointsEngine,
                              TaskMapper taskMapper
                              ) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.rsePointsEngine = rsePointsEngine;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<ProjectResponse> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse findProjectById(String id) {
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("project not found"));
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.fromRequest(request);
        Project createdProject = projectRepository.save(project);
        return projectMapper.toResponse(createdProject);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(String id, ProjectRequest request) {
        Project projectToUpdate = projectRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("project not found"));

        // 1. Mise à jour des informations de base
        projectToUpdate.updateDetails(request.name(), request.description());

        // 2. Synchronisation de la liste des tâches
        synchronizeTasks(projectToUpdate, request.tasks());

        projectRepository.save(projectToUpdate);
        return projectMapper.toResponse(projectToUpdate);
    }

    /**
     * Gère l'aiguillage de la synchronisation des tâches du projet.
     */
    private void synchronizeTasks(Project project, List<ProjectTaskRequest> incomingTasks) {
        if (incomingTasks == null || incomingTasks.isEmpty()) {
            project.getTasks().clear();
            return;
        }

        // Étape A : Nettoyer l'existant en supprimant ce qui n'est plus là
        removeOrphanTasks(project, incomingTasks);

        // Étape B : Traiter le reste (Mise à jour des anciennes ou ajout des nouvelles)
        processIncomingTasks(project, incomingTasks);
    }

    /**
     * Supprime du projet les tâches qui ont été retirées côté Front-end.
     */
    private void removeOrphanTasks(Project project, List<ProjectTaskRequest> incomingTasks) {
        project.getTasks().removeIf(existingTask ->
                incomingTasks.stream().noneMatch(reqTask ->
                        reqTask.id() != null && UUID.fromString(reqTask.id()).equals(existingTask.getId())
                )
        );
    }

    /**
     * Parcourt les tâches reçues pour soit mettre à jour l'existant, soit ajouter une nouveauté.
     */
    private void processIncomingTasks(Project project, List<ProjectTaskRequest> incomingTasks) {
        incomingTasks.forEach(reqTask -> {
            if (reqTask.id() != null) {
                updateExistingTask(project, reqTask);
            } else {
                project.addTask(taskMapper.fromRequest(reqTask));
            }
        });
    }

    /**
     * Recherche une tâche existante par son ID pour modifier ses propriétés métier.
     */
    private void updateExistingTask(Project project, ProjectTaskRequest reqTask) {
        project.getTasks().stream()
                .filter(existing -> existing.getId().equals(UUID.fromString(reqTask.id())))
                .findFirst()
                .ifPresent(existing -> {
                    existing.updateTitle(reqTask.title());
                    existing.changeStatus(reqTask.status());
                });
    }

    @Override
    @Transactional
    public void deleteProject(String id) {
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("project not found"));
        if(project != null) projectRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public int calculateRseScore(List<ProjectResponse> projects) {

        return projects.stream()
                .filter(project -> project.tasks() != null)
                .flatMap(project -> project.tasks().stream())
                .mapToInt(rsePointsEngine::calculateTotalScore)
                .sum(); // On additionne le tout
    }
}
