<script setup lang="ts">
import IconButton from "@/components/atoms/IconButton.vue";

defineProps<{
  title: string;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  (e: 'remove'): void;
}>();
</script>

<template>
  <li class="task-item">
    <span class="task-title">{{ title }}</span>

    <IconButton
      variant="danger"
      :disabled="disabled"
      @click="emit('remove')"
    >
      <!-- Icône de croix (X) -->
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <line x1="18" y1="6" x2="6" y2="18"></line>
        <line x1="6" y1="6" x2="18" y2="18"></line>
      </svg>
    </IconButton>
  </li>
</template>

<style scoped>
.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0.75rem;
  background-color: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  gap: 1rem;
}

.task-title {
  font-size: 0.9rem;
  color: #334155;
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
