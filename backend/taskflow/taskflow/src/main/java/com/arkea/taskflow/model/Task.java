package com.arkea.taskflow.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String status = "A_FAIRE";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;

    public Task() {}
    public Task(String title) {
        this.title = title;
        this.status = "A_FAIRE";
    }
    public Task(String title, String status) {
        this.title = title;
        this.status = (status != null) ? status : "A_FAIRE";
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String title;
        private String status;

        private Builder() {}
        public Builder title(String title) { this.title = title; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public Task build() {
            if(status == null){
               return new Task(title);
            }
            return new Task(title,status);
        }
    }
}
