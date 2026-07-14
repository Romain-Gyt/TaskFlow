<script setup lang="ts">
// On coupe l'héritage pour que les attributs (comme type ou disabled) aillent bien sur le <button>
defineOptions({
  inheritAttrs: false
});

defineProps<{
  label: string; // Utilise 'string' en minuscule (meilleure pratique TS que 'String')
  variant?: 'primary' | 'danger';
}>();

const emit = defineEmits<{
  (e: 'click'): void;
}>();
</script>

<template>
  <button
    :class="['btn', variant || 'primary']"
    v-bind="$attrs"
    @click="emit('click')"
  >
    {{ label }}
  </button>
</template>

<style scoped>
.btn {
  padding: 0.75rem; /* Ajusté pour matcher la hauteur des inputs du login */
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  transition: background-color 0.15s ease;
}
.btn:disabled {
  background-color: #94a3b8;
  cursor: not-allowed;
}
.primary { background-color: #0056b3; color: white; }
.primary:hover:not(:disabled) { background-color: #004085; }

.danger { background-color: #dc2626; color: white; }
.danger:hover:not(:disabled) { background-color: #b91c1c; }
</style>
