package com.arkea.taskflow.controller;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;

import com.arkea.taskflow.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        List<ProjectResponse> projects = projectService.findAllProjects();
        log.info("[API GET] Liste des projets récupérée. Nombre de projets : {}", projects);
        return projects;
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable String id) {
        return projectService.findProjectById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project){
        log.info("Creation de la tache: {}", project);
       ProjectResponse createdProject = projectService.createProject(project);

       URI location = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/{id}")
               .buildAndExpand(createdProject.id())
               .toUri();
        return ResponseEntity
                .created(location)
                .body(createdProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable String id,
            @RequestBody ProjectRequest projectRequest
    ) {
        log.info("[API PUT] Requête de mise à jour reçue pour l'ID : {} avec le corps : {}", id, projectRequest);
        if(projectRequest.id() != null && !projectRequest.id().equals(id) )throw new IllegalArgumentException("Incohérence détectée : L'ID de la requête ne correspond pas à l'ID de l'URL.");
        ProjectResponse updatedProject = projectService.updateProject(id, projectRequest);
        log.info("Mise a jour du projet : {}", updatedProject);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rse-score")
    public ResponseEntity<Integer> getGlobalRseScore() {
        // 1. Récupérer tous les projets de la base
        List<ProjectResponse> projects = projectService.findAllProjects();

        // 2. Calculer le score global
        int totalScore = projectService.calculateRseScore(projects);

        // 3. Renvoyer le score brut
        return ResponseEntity.ok(totalScore);
    }

}
