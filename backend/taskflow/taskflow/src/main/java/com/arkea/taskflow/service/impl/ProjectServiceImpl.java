package com.arkea.taskflow.service.impl;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.mapper.ProjectMapper;
import com.arkea.taskflow.mapper.TaskMapper;
import com.arkea.taskflow.model.Project;
import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.repository.ProjectRepository;
import com.arkea.taskflow.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectMapper projectMapper,  ProjectRepository projectRepository) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
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
        Project projectToUpdate = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("project not found"));
        projectToUpdate.updateDetails(request.name(),request.description());
        projectRepository.save(projectToUpdate);
        return projectMapper.toResponse(projectToUpdate);
    }

    @Override
    @Transactional
    public void deleteProject(String id) {
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("project not found"));
        if(project != null) projectRepository.deleteById(UUID.fromString(id));
    }
}
