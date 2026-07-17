package com.arkea.taskflow.dto;

import java.util.List;

public record ProjectRequest( String id, String name, String description, List<ProjectTaskRequest> tasks) {
    public ProjectRequest{
        if(tasks==null){
            tasks = tasks != null ? List.copyOf(tasks) : List.of();
        }
    }
}
