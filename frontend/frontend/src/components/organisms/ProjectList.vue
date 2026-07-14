<script setup lang="ts">
import { onMounted } from "vue";
import TaskCard from "@/components/molecules/TaskCard.vue";
import { useProjectStore } from "@/stores/project.store.ts";
import { storeToRefs } from "pinia";
import type { Task } from "@/types/task.types.ts";

const store = useProjectStore();
const { projects, isLoading, error, canUndo, canRedo } = storeToRefs(store);
const { loadProjects, deleteLocalTask, undo, redo } = store;

onMounted(async () => {
  await loadProjects();
});

// Gère proprement la suppression sans curryfication
const handleTaskDelete = (projectId: string, taskId: string) => {
  deleteLocalTask(projectId, taskId);
};

// Fonction centralisée pour filtrer les tâches en évitant les calculs lourds dans le template
const getTasksByStatus = (tasks: Task[], status: string) => {
  if (!tasks) return [];
  return tasks.filter(t => t.status === status);
};
</script>

<template>
  <div class="project-box">
    <h2>Tableau Kanban - Socle Arkéa</h2>
    <div class="history-controls">
      <button :disabled="!canUndo" @click="undo" class="ctrl-btn">↩ Annuler</button>
      <button :disabled="!canRedo" @click="redo" class="ctrl-btn">↪ Rétablir</button>
    </div>
    <div v-if="isLoading" class="info">Chargement et validation de l'état...</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <div v-else class="project-grid">
      <div v-for="project in projects" :key="project.id" class="project-card">
        <h3>{{ project.name }}</h3>
        <p class="desc">{{ project.description }}</p>

        <div class="kanban-board">
          <!-- Colonne À Faire -->
          <div class="column">
            <h4>À Faire</h4>
            <TaskCard
              v-for="task in getTasksByStatus(project.tasks, 'A_FAIRE')"
              :key="task.id"
              :task="task"
              @delete="(taskId) => handleTaskDelete(project.id, taskId)"
            />
          </div>

          <!-- Colonne En Cours -->
          <div class="column">
            <h4>En Cours</h4>
            <TaskCard
              v-for="task in getTasksByStatus(project.tasks, 'EN_COURS')"
              :key="task.id"
              :task="task"
              @delete="(taskId) => handleTaskDelete(project.id, taskId)"
            />
          </div>

          <!-- Colonne Terminé -->
          <div class="column">
            <h4>Terminé</h4>
            <TaskCard
              v-for="task in getTasksByStatus(project.tasks, 'TERMINEE')"
              :key="task.id"
              :task="task"
              @delete="(taskId) => handleTaskDelete(project.id, taskId)"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.project-box { padding: 1.5rem; font-family: sans-serif; }
.project-grid { display: flex; flex-direction: column; gap: 2rem; }
.project-card { border: 1px solid #ccc; padding: 1.25rem; border-radius: 6px; background-color: #fff; }
.desc { color: #666; font-size: 0.9rem; margin-bottom: 1rem; }
.kanban-board { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem; margin-top: 1rem; }
.column { background: #f4f5f7; padding: 0.75rem; border-radius: 4px; min-height: 150px; }
.column h4 { margin-top: 0; margin-bottom: 0.75rem; color: #333; font-size: 0.9rem; border-bottom: 2px solid #ddd; padding-bottom: 0.25rem; }
.error { color: #d32f2f; font-weight: bold; }
.history-controls { margin-bottom: 1rem; display: flex; gap: 0.5rem; }
.ctrl-btn { padding: 0.5rem 1rem; cursor: pointer; border-radius: 4px; border: 1px solid #ccc; background: #fff; }
.ctrl-btn:disabled { opacity: 0.5; cursor: not-allowed; }
</style>
