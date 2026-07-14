<script setup lang="ts">
interface Props {
  label: string;
  required?: boolean;
  forId?: string;
}

withDefaults(defineProps<Props>(), {
  required: false,
  forId: ''
});
</script>

<template>
  <label :for="forId" class="custom-label">
    <span class="label-text">{{ label }}</span>
    <span v-if="required" class="required-asterisk" aria-hidden="true">*</span>
  </label>
</template>

<style scoped>
.custom-label {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
  font-size: 0.87rem;
  font-weight: 600;
  color: #334155; /* Couleur gris ardoise moderne */
  user-select: none;
  margin-bottom: 0.25rem;
  cursor: pointer;
}

/* Style de l'astérisque pour les champs obligatoires */
.required-asterisk {
  color: #dc2626; /* Rouge Arkéa / Alerte */
  font-size: 0.9rem;
  font-weight: 700;
}

/* Optionnel : Changement de couleur si le champ associé est survolé */
.custom-label:hover {
  color: #1e293b;
}
</style>
