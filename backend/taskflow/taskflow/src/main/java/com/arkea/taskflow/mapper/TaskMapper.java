package com.arkea.taskflow.mapper;

import com.arkea.taskflow.dto.TaskResponse;
import com.arkea.taskflow.model.Task;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    TaskResponse toResponse(Task task);
}
