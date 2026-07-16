package com.arkea.taskflow.service;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.model.Project;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> findAllProjects();
    ProjectResponse createProject(ProjectRequest project);
}
