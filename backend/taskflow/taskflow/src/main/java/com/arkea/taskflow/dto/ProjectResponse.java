package com.arkea.taskflow.dto;

import com.arkea.taskflow.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ProjectResponse(UUID id, String name, String description, List<TaskResponse> tasks) {
    public ProjectResponse{
        if(tasks==null){
            tasks=new ArrayList<>();
        }
    }
}
