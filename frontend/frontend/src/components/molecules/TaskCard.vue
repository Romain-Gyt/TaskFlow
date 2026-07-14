<script setup lang="ts">
import type {Task} from "@/types/task.types.ts";
import BaseButton from "@/components/atoms/BaseButton.vue";

const props = defineProps<{
  task: Task
}>();
const emit = defineEmits<{
  (e: 'delete',taskId: string): void;
}>();

const onDeleteClick = () => {
  if(confirm("Voulez-vous supprimer cette tâche ?")){
    emit('delete', props.task.id);
  }
}

</script>
<template>
  <div class="task-card">
    <span class="task-title"> {{ task.title }}</span>
    <BaseButton
      label="Supprimer"
      variant="danger"
      @click="onDeleteClick"
    />
  </div>
</template>
<style scoped>
.task-card {
  display: flex; justify-content: space-between; align-items: center;
  padding: 0.75rem; background-color: #f9f9f9; border: 1px solid #eee;
  border-radius: 4px; margin-bottom: 0.5rem;
}
.task-title { font-size: 0.95rem; color: #222; }
</style>
