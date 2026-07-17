<script setup lang="ts">
import { onMounted } from "vue";
import { useProjectStore } from "@/stores/project.store.ts";
import { storeToRefs } from "pinia";

const store = useProjectStore();
const { rseScore, isRseLoading } = storeToRefs(store);

</script>

<template>
  <div class="dashboard-layout">
    <aside class="sidebar">
      <h3>Socle Arkéa</h3>
      <nav>
        <RouterLink to="/dashboard/kanban" class="nav-link">📋 Kanban Board</RouterLink>
        <RouterLink to="/dashboard/project" class="nav-link">Créer projet</RouterLink>
      </nav>
    </aside>

    <main class="main-content">
      <header class="dashboard-header">
        <h1>Espace de Travail</h1>

        <!-- 💡 Intégration du score RSE à droite dans le header -->
        <div class="rse-badge" :class="{ 'is-loading': isRseLoading }">
          <span class="rse-icon">🌱</span>
          <span class="rse-label">Score RSE Global :</span>
          <span v-if="isRseLoading" class="mini-spinner"></span>
          <span v-else class="rse-value">{{ rseScore }} pts</span>
        </div>
      </header>

      <div class="page-content">
        <RouterView />
      </div>
    </main>
  </div>
</template>

<style scoped>
.dashboard-layout {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 250px;
  background-color: #004b87; /* Bleu Arkéa */
  color: white;
  padding: 1.5rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  display: block;
  padding: 0.5rem 0;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.dashboard-header {
  background: white;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #ddd;
  /* 💡 Flexbox pour aligner le titre à gauche et le score RSE à droite */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dashboard-header h1 {
  margin: 0;
  font-size: 1.5rem;
}

/* 💡 Styles du Badge RSE */
.rse-badge {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background-color: #f0fdf4; /* Vert ardoise ultra clair */
  border: 1px solid #bbf7d0; /* Bordure verte */
  padding: 0.5rem 0.875rem;
  border-radius: 9999px; /* Forme de pilule */
  font-size: 0.9rem;
  font-weight: 600;
  color: #166534;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

.rse-value {
  font-weight: 700;
  color: #15803d; /* Vert plus prononcé pour la valeur numérique */
}

.mini-spinner {
  width: 14px;
  height: 14px;
  border: 2px solid rgba(22, 101, 52, 0.2);
  border-radius: 50%;
  border-top-color: #166534;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.page-content {
  padding: 1.5rem;
  background-color: #f8fafc;
  flex: 1;
}
</style>
