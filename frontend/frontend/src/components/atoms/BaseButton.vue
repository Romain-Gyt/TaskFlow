<script setup lang="ts">
 defineProps<{
  label: String;
  variant ?: 'primary' | 'danger';
}>()

const emit = defineEmits<{
  (e: 'click'): void;
}>()

</script>

<template>
  <button
    :class="['btn', variant || 'primary']"
    @click="emit('click')"
  >
    {{ label }}
  </button>
</template>

<style scoped>
.btn { padding: 0.5rem 1rem; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
.primary { background-color: #004b87; color: white; }
.danger { background-color: #d32f2f; color: white; }
</style>
