package com.arkea.taskflow.repository;

import com.arkea.taskflow.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Override
    @EntityGraph(attributePaths = {"tasks"})
    List<Project> findAll();
    Optional<Project> findById(UUID id);

}
