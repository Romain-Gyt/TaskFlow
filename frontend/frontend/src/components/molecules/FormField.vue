<script setup lang="ts">
import BaseLabel from "@/components/atoms/BaseLabel.vue";
import BaseInput from "@/components/atoms/BaseInput.vue";

defineOptions({
  inheritAttrs: false
});

const modelValue = defineModel<string>({ default: "" });

defineProps<{
  label: string;
  error?: string;
}>();

const uniqueId = `field-${Math.random().toString(36).substring(2, 9)}`;
</script>

<template>
  <div class="field-container">
    <!-- Le label prend juste sa prop dédiée -->
    <BaseLabel :label="label" :forId="uniqueId" />
    <BaseInput
      v-model="modelValue"
      :id="uniqueId"
      :hasError="!!error"
      v-bind="$attrs"
    />
    <span v-if="error" class="error-message">{{ error }}</span>
  </div>
</template>
