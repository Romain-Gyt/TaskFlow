package com.arkea.taskflow.service;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectResponse> findAllProjects();
    ProjectResponse findProjectById(String id);
    ProjectResponse createProject(ProjectRequest project);
    ProjectResponse updateProject(String id,ProjectRequest project);
    void deleteProject(String id);
    int calculateRseScore(List<ProjectResponse> projects);
}
