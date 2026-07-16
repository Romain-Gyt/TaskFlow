package com.arkea.taskflow.mapper;

import com.arkea.taskflow.dto.ProjectRequest;
import com.arkea.taskflow.dto.ProjectResponse;
import com.arkea.taskflow.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses =  {TaskMapper.class})
public interface ProjectMapper {
    Project fromRequest(ProjectRequest request);
    ProjectResponse toResponse(Project project);
}
