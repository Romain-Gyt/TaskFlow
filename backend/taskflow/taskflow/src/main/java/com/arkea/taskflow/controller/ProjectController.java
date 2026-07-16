package com.arkea.taskflow.controller;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public List<ProjectResponse> getAllProjects() throws InterruptedException {
        log.info("Traitement de la requête sur le thread : {}", Thread.currentThread());
        Thread.sleep(200);
        return projectService.findAllProjects();
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project){
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

}
