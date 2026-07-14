<script setup lang="ts">
import ProjectList from "@/components/organisms/ProjectList.vue";

</script>

<template>
  <div class="project-box">
    <ProjectList />
  </div>
</template>

<style scoped>
.project-box { padding: 1.5rem; font-family: sans-serif; }
.card { border: 1px solid #ddd; padding: 1rem; margin-bottom: 1rem; list-style-type: none; border-radius: 4px; }
.error { color: #d32f2f; font-weight: bold; }
.info { color: #555; }
</style>

