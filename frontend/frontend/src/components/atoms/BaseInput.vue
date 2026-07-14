<script setup lang="ts">
// Utilisation de la macro de Vue 3.4+ pour gérer le v-model proprement
const modelValue = defineModel<string>({ default: "" });

interface Props {
  type?: "text" | "email" | "password" | "number" | "url";
  id?: string;
  placeholder?: string;
  required?: boolean;
  disabled?: boolean;
  hasError?: boolean;
}

withDefaults(defineProps<Props>(), {
  type: "text",
  id: "",
  placeholder: "",
  required: false,
  disabled: false,
  hasError: false
});
</script>

<template>
  <input
    v-model="modelValue"
    :type="type"
    :id="id"
    :placeholder="placeholder"
    :required="required"
    :disabled="disabled"
    class="custom-input"
    :class="{ 'input-error': hasError }"
  />
</template>

<style scoped>
.custom-input {
  width: 100%;
  padding: 0.75rem 1rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
  font-size: 0.95rem;
  color: #1e293b;
  background-color: #ffffff;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  outline: none;
  box-sizing: border-box;
  transition: border-color 0.15s ease, box-shadow 0.15s ease;
}

/* État : Survol */
.custom-input:hover:not(:disabled) {
  border-color: #94a3b8;
}

/* État : Focus (Sélectionné) */
.custom-input:focus:not(:disabled) {
  border-color: #0056b3; /* Bleu Arkéa */
  box-shadow: 0 0 0 3px rgba(0, 86, 179, 0.15);
}

/* État : Erreur */
.custom-input.input-error {
  border-color: #dc2626;
  background-color: #fef2f2;
}

.custom-input.input-error:focus {
  box-shadow: 0 0 0 3px rgba(220, 38, 38, 0.15);
}

/* État : Désactivé */
.custom-input:disabled {
  background-color: #f1f5f9;
  color: #64748b;
  border-color: #e2e8f0;
  cursor: not-allowed;
}

/* Style du placeholder */
.custom-input::placeholder {
  color: #94a3b8;
}
</style>
