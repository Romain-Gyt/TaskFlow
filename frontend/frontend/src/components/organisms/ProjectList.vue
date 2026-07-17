<!-- src/components/organisms/ProjectList.vue -->
<script setup lang="ts">
import { onMounted } from "vue";
import ProjectCard from "@/components/molecules/ProjectCard.vue";
import { useProjectStore } from "@/stores/project.store.ts";
import { storeToRefs } from "pinia";

const store = useProjectStore();
const { projects, isLoading, fetchError, isDeleting, deleteError, canUndo, canRedo } = storeToRefs(store);
const { loadProjects, deleteProject, deleteLocalTask, undo, redo } = store;

onMounted(async () => {
  await loadProjects();
});

// Centralisation des suppressions dans le parent orchestrateur
const handleProjectDelete = (projectId: string) => {
    deleteProject(projectId);
};

const handleTaskDelete = (projectId: string, taskId: string) => {
  deleteLocalTask(projectId, taskId);
};
</script>

<template>
  <div class="project-box">
    <h2>Tableau Kanban - Socle Arkéa</h2>

    <!-- Contrôles d'historique -->
    <div class="history-controls">
      <button :disabled="!canUndo" @click="undo" class="ctrl-btn">↩ Annuler</button>
      <button :disabled="!canRedo" @click="redo" class="ctrl-btn">↪ Rétablir</button>
    </div>

    <!-- États globaux -->
    <div v-if="isLoading" class="info">Chargement et validation de l'état...</div>
    <div v-else-if="fetchError" class="error">{{ fetchError }}</div>
    <div v-else-if="deleteError" class="error-banner">
      ⚠️ Impossible de supprimer le projet : {{ deleteError }}
    </div>

    <!-- Grille de projets -->
    <div v-else class="project-grid">
      <ProjectCard
        v-for="project in projects"
        :key="project.id"
        :project="project"
        :is-deleting="isDeleting"
        @delete-project="handleProjectDelete"
        @delete-task="handleTaskDelete"
      />
    </div>
  </div>
</template>

<style scoped>
.project-box { padding: 1.5rem; font-family: sans-serif; }
.project-grid { display: flex; flex-direction: column; gap: 2rem; }
.error { color: #d32f2f; font-weight: bold; }
.error-banner { background-color: #fff5f5; border: 1px dashed #e53e3e; color: #c53030; padding: 0.75rem 1rem; border-radius: 4px; margin-bottom: 1.5rem; font-weight: 500; }
.history-controls { margin-bottom: 1rem; display: flex; gap: 0.5rem; }
.ctrl-btn { padding: 0.5rem 1rem; cursor: pointer; border-radius: 4px; border: 1px solid #ccc; background: #fff; }
.ctrl-btn:disabled { opacity: 0.5; cursor: not-allowed; }
</style>
