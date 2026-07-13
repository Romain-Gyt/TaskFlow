<script setup lang="ts">
import { onMounted } from "vue";
import { useProjects } from "@/composable/useProjects.ts";

const { projects, loading, error, fetchProjects } = useProjects();

onMounted(() => {
  fetchProjects();
})

</script>

<template>
  <div class="project-box">
    <h2> Projets actif (Socle Arkéa)</h2>

    <div v-if="loading" class="info">Désérialisation et validation zod en cours.... </div>
    <div v-else-if="error" class="error"> {{ error }}</div>

    <ul v-else>
      <li v-for="project in projects" :key="project.id" class="card">
        <h3>{{ project.name}}</h3>
        <p>{{ project.description }}</p>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.project-box { padding: 1.5rem; font-family: sans-serif; }
.card { border: 1px solid #ddd; padding: 1rem; margin-bottom: 1rem; list-style-type: none; border-radius: 4px; }
.error { color: #d32f2f; font-weight: bold; }
.info { color: #555; }
</style>

