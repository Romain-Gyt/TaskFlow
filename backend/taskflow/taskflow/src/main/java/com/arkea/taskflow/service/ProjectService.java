package com.arkea.taskflow.service;

import com.arkea.taskflow.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> findAllProjects();
}
