<script setup lang="ts">
defineOptions({
  inheritAttrs: false
});

defineProps<{
  variant?: 'danger' | 'primary' | 'secondary';
}>();

const emit = defineEmits<{
  (e: 'click'): void;
}>();
</script>

<template>
  <button
    :class="['icon-btn', variant || 'secondary']"
    v-bind="$attrs"
    type="button"
    @click="emit('click')"
  >
    <!-- Slot pour injecter n'importe quel SVG à l'utilisation -->
    <slot />
  </button>
</template>

<style scoped>
.icon-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border: 1px solid transparent;
  border-radius: 6px;
  cursor: pointer;
  background-color: transparent;
  transition: all 0.15s ease;
  color: #64748b; /* Slate 400 */
}

.icon-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Variantes */
.secondary:hover:not(:disabled) {
  background-color: #f1f5f9;
  color: #0f172a;
}

.danger:hover:not(:disabled) {
  background-color: #fef2f2;
  color: #ef4444;
}

.primary:hover:not(:disabled) {
  background-color: #e0f2fe;
  color: #0284c7;
}
</style>
