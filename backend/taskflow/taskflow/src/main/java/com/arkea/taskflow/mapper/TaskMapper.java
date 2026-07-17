package com.arkea.taskflow.mapper;

import com.arkea.taskflow.dto.CreateTaskRequest;
import com.arkea.taskflow.dto.ProjectTaskRequest;
import com.arkea.taskflow.dto.TaskResponse;
import com.arkea.taskflow.model.Task;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TaskMapper {
    Task fromRequest(ProjectTaskRequest taskRequest);
    Task fromTaskRequest(CreateTaskRequest taskRequest);
    @Mapping(target = "projectId", source = "project.id")
    TaskResponse toResponse(Task task);
}
