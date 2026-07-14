<script setup lang="ts">

import {onMounted, ref} from "vue";
import {useProjects} from "@/composable/useProjects.ts";
import type {Project} from "@/types/project.types.ts";
import TaskCard from "@/components/molecules/TaskCard.vue";

const { projects: initialProjects, loading, error, fetchProjects } = useProjects();
const localProjects = ref<Project[]>([]);

onMounted(async () => {
   await fetchProjects();
  localProjects.value = initialProjects.value;
});

const handleTaskDelete = (projectId: string) => (taskId: string) => {
  const project = localProjects.value.find(p => p.id === projectId);
  if (project) {
    project.tasks = project.tasks.filter(task => task.id !== taskId);
  }
};

</script>

<template>
  <div class="project-box">
    <h2>Tableau Kanban - Socle Arkéa</h2>

    <div v-if="loading" class="info">Chargement et validation de l'état...</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <div v-else class="project-grid">
      <div v-for="project in localProjects" :key="project.id" class="project-card">
        <h3>{{ project.name }}</h3>
        <p class="desc">{{ project.description }}</p>

        <!-- Vue Kanban découpée en 3 colonnes de statut -->
        <!-- Vue Kanban découpée en 3 colonnes de statut -->
        <div class="kanban-board">
          <div class="column">
            <h4>À Faire</h4>
            <TaskCard
              v-for="task in project.tasks.filter(t => t.status === 'A_FAIRE')"
              :key="task.id"
              :task="task"
              @delete="handleTaskDelete(project.id)"
            />
          </div>

          <div class="column">
            <h4>En Cours</h4>
            <TaskCard
              v-for="task in project.tasks.filter(t => t.status === 'EN_COURS')"
              :key="task.id"
              :task="task"
              @delete="handleTaskDelete(project.id)"
            />
          </div>

          <div class="column">
            <h4>Terminé</h4>
            <TaskCard
              v-for="task in project.tasks.filter(t => t.status === 'TERMINEE')"
              :key="task.id"
              :task="task"
              @delete="handleTaskDelete(project.id)"
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
</style>
