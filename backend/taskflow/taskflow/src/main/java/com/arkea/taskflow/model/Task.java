package com.arkea.taskflow.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 1. Constructeur JPA (protected pour Hibernate)
    protected Task() {}

    // 2. Constructeur privé pour le Builder
    private Task(Builder builder) {
        this.title = builder.title;
        // Valeur par défaut "A_FAIRE" si aucun statut n'est fourni
        this.status = (builder.status != null && !builder.status.isBlank()) ? builder.status : "A_FAIRE";
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public Project getProject() { return project; }

    // Setters d'association (Utilisé par le Project et Hibernate)
    public void setProject(Project project) {
        this.project = project;
    }

    // Méthodes métier explicites pour la mutation
    public void updateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre de la tâche ne peut pas être vide.");
        }
        this.title = title;
    }

    public void changeStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Le statut de la tâche ne peut pas être vide.");
        }
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Sécurisé : On n'affiche JAMAIS l'objet "project" pour éviter le StackOverflowError
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    // Le Builder Statique
    public static class Builder {
        private UUID id;
        private String title;
        private String status;

        private Builder() {}

        public Builder id(String id) {
            if(id != null && !id.isBlank()) this.id = UUID.fromString(id);
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Task build() {
          Objects.requireNonNull(title,"Title is required");
            return new Task(this);
        }
    }
}