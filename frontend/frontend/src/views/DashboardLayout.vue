<script setup lang="ts">
// On n'importe PAS ProjectList ici. Le routeur s'en occupe tout seul !
</script>

<template>
  <div class="dashboard-layout">
    <aside class="sidebar">
      <h3>Socle Arkéa</h3>
      <nav>
        <!-- RouterLink permet de naviguer sans recharger la page -->
        <RouterLink to="/dashboard/kanban" class="nav-link">📋 Kanban Board</RouterLink>
      </nav>
    </aside>

    <main class="main-content">
      <header class="dashboard-header">
        <h1>Espace de Travail</h1>
      </header>

      <div class="page-content">
        <!-- C'est ICI que ton composant ProjectList.vue va s'injecter automatiquement -->
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
}

.page-content {
  padding: 1.5rem;
  background-color: #f8fafc;
  flex: 1;
}
</style>
