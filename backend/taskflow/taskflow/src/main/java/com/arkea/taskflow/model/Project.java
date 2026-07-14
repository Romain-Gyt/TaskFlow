package com.arkea.taskflow.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Project() {}

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setProject(null);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public static Builder builder(){
        return new Builder();
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    public static class Builder{
        private String name;
        private String description;

        private Builder(){}
        public Builder name(String name){ this.name = name; return this; }
        public Builder description(String description){ this.description = description; return this; }
        public Project build(){
            return new Project(name,description);
        }
    }
}
