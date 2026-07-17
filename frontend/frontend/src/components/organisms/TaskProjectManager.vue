<script setup lang="ts">
import TaskInputRow from "@/components/molecules/TaskInputRow.vue";
import TaskItem from "@/components/molecules/TaskItem.vue";

// Représente la liste des tâches à envoyer à l'API (sans ID à la création)
interface ProjectTaskRequest {
  title: string;
  status: string;

}

// v-model bidirectionnel lié aux tasks du formulaire parent
const tasks = defineModel<ProjectTaskRequest[]>({ default: () => [] });

defineProps<{
  disabled?: boolean;
}>();

const addTask = (title: string) => {
  tasks.value.push({
    title,
    status: "A_FAIRE"
  });
};

const removeTask = (index: number) => {
  tasks.value.splice(index, 1);
};
</script>

<template>
  <div class="tasks-section">
    <div class="tasks-header">
      <h2>Tâches initiales</h2>
      <p class="section-subtitle">Ajoutez des tâches qui seront créées en même temps que le projet.</p>
    </div>

    <!-- Saisie -->
    <TaskInputRow :disabled="disabled" @add="addTask" />

    <!-- Liste -->
    <ul v-if="tasks.length > 0" class="added-tasks-list">
      <TaskItem
        v-for="(task, index) in tasks"
        :key="index"
        :title="task.title"
        :disabled="disabled"
        @remove="removeTask(index)"
      />
    </ul>

    <div v-else class="empty-tasks">
      Aucune tâche initiale ajoutée pour le moment.
    </div>
  </div>
</template>

<style scoped>
.tasks-section {
  border-top: 1px solid #e2e8f0;
  padding-top: 1.5rem;
  margin-top: 0.5rem;
}

.tasks-header h2 {
  font-size: 1.1rem;
  font-weight: 600;
  color: #0f172a;
}

.section-subtitle {
  font-size: 0.85rem;
  color: #64748b;
  margin-bottom: 1rem;
}

.added-tasks-list {
  list-style: none;
  padding: 0;
  margin: 1rem 0 0 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  max-height: 180px;
  overflow-y: auto;
}

.empty-tasks {
  margin-top: 1rem;
  font-size: 0.85rem;
  color: #94a3b8;
  text-align: center;
  font-style: italic;
  padding: 1rem;
  border: 1px dashed #cbd5e1;
  border-radius: 8px;
}
</style>
