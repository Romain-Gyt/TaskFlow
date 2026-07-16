<script setup lang="ts">
import { ref } from "vue";
import BaseButton from "@/components/atoms/BaseButton.vue";

defineProps<{
  disabled?: boolean;
}>();

const emit = defineEmits<{
  (e: 'add', taskTitle: string): void;
}>();

const taskTitle = ref("");

const handleAdd = () => {
  const trimmed = taskTitle.value.trim();
  if (trimmed) {
    emit('add', trimmed);
    taskTitle.value = ""; // Réinitialise l'input local après envoi
  }
};
</script>

<template>
  <div class="task-input-row">
    <input
      v-model="taskTitle"
      type="text"
      placeholder="Nom de la tâche..."
      :disabled="disabled"
      class="task-inline-input"
      @keydown.enter.prevent="handleAdd"
    />
    <BaseButton
      label="Ajouter"
      variant="primary"
      class="btn-add"
      :disabled="disabled || !taskTitle.trim()"
      @click="handleAdd"
    />
  </div>
</template>

<style scoped>
.task-input-row {
  display: flex;
  gap: 0.75rem;
}

.task-inline-input {
  flex: 1;
  padding: 0.6rem 0.875rem;
  font-size: 0.9rem;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  outline: none;
  background-color: #ffffff;
  transition: border-color 0.15s ease;
}

.task-inline-input:focus {
  border-color: #0056b3;
}

.task-inline-input:disabled {
  background-color: #f1f5f9;
  cursor: not-allowed;
}

.btn-add {
  padding: 0.6rem 1rem;
  background-color: #f1f5f9;
  border: 1px solid #cbd5e1;
  color: #334155;
  font-weight: 600;
  font-size: 0.875rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.15s ease;
}

.btn-add:hover:not(:disabled) {
  background-color: #e2e8f0;
}

.btn-add:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
