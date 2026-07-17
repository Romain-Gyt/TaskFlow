<!-- src/components/molecules/ProjectCard.vue -->
<script setup lang="ts">
import TaskCard from "@/components/molecules/TaskCard.vue";
import type { Project } from "@/types/project.types.ts";
import type { Task } from "@/types/task.types.ts";
import BaseButton from "@/components/atoms/BaseButton.vue";

// 1. Définition des entrées (Props)
const props = defineProps<{
  project: Project;
  isDeleting: boolean;
}>();

// 2. Définition des sorties (Events)
const emit = defineEmits<{
  (e: 'deleteProject', projectId: string): void;
  (e: 'deleteTask', projectId: string, taskId: string): void;
}>();

const onDeleteClick = () => {
  if(confirm("Voulez-vous supprimer ce Project? ?")){
    emit('deleteProject',props.project.id)
  }
}
// Logique purement visuelle déportée ici
const getTasksByStatus = (tasks: Task[], status: string) => {
  if (!tasks) return [];
  return tasks.filter(t => t.status === status);
};
</script>

<template>
  <div class="project-card">
    <div class="project-header">
      <div>
        <h3>{{ project.name }}</h3>
        <p class="desc">{{ project.description }}</p>
      </div>
      <div class="action-buttons">
        <!-- 💡 Bouton Modifier qui utilise le nom de la route et lui passe l'ID du projet -->
        <RouterLink :to="{ name: 'project-edit', params: { id: project.id } }">
          <BaseButton
            label="Modifier"
            variant="primary"
            class="ctrl-btn edit-btn"
          />
        </RouterLink>

        <BaseButton
          label="Supprimer"
          variant="danger"
          :disabled="isDeleting"
          @click="onDeleteClick"
          class="ctrl-btn delete-btn"
        />
      </div>
    </div>

    <div class="kanban-board">
      <!-- Colonne À Faire -->
      <div class="column">
        <h4>À Faire</h4>
        <TaskCard
          v-for="task in getTasksByStatus(project.tasks, 'A_FAIRE')"
          :key="task.id"
          :task="task"
          @delete="(taskId) => emit('deleteTask', project.id, taskId)"
        />
      </div>

      <!-- Colonne En Cours -->
      <div class="column">
        <h4>En Cours</h4>
        <TaskCard
          v-for="task in getTasksByStatus(project.tasks, 'EN_COURS')"
          :key="task.id"
          :task="task"
          @delete="(taskId) => emit('deleteTask', project.id, taskId)"
        />
      </div>

      <!-- Colonne Terminé -->
      <div class="column">
        <h4>Terminé</h4>
        <TaskCard
          v-for="task in getTasksByStatus(project.tasks, 'TERMINEE')"
          :key="task.id"
          :task="task"
          @delete="(taskId) => emit('deleteTask', project.id, taskId)"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.project-card { border: 1px solid #ccc; padding: 1.25rem; border-radius: 6px; background-color: #fff; }
.project-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 1rem; }
.project-header h3 { margin: 0 0 0.25rem 0; }
.desc { color: #666; font-size: 0.9rem; margin-bottom: 1rem; }
.kanban-board { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem; margin-top: 1rem; }
.column { background: #f4f5f7; padding: 0.75rem; border-radius: 4px; min-height: 150px; }
.column h4 { margin-top: 0; margin-bottom: 0.75rem; color: #333; font-size: 0.9rem; border-bottom: 2px solid #ddd; padding-bottom: 0.25rem; }
.ctrl-btn { padding: 0.5rem 1rem; cursor: pointer; border-radius: 4px; border: 1px solid #ccc; background: #fff; }
.ctrl-btn:disabled { opacity: 0.5; cursor: not-allowed; }
.delete-btn { background-color: #fff1f1; border-color: #f5c2c2; color: #c53030; transition: all 0.2s ease; }
.delete-btn:hover:not(:disabled) { background-color: #c53030; color: #fff; border-color: #c53030; }
</style>
