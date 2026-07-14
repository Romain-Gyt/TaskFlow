<script setup lang="ts">
import LoginForm from "@/components/organisms/LoginForm.vue";
</script>

<template>
  <div class="login-view">
    <!-- Fond décoratif subtil en arrière-plan -->
    <div class="background-blob-1"></div>
    <div class="background-blob-2"></div>

    <!-- Ton composant de formulaire autonome -->
    <LoginForm />
  </div>
</template>

<style scoped>
.login-view {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100vw;
  background-color: #f4f6f9; /* Gris clair pro */
  overflow: hidden;
  box-sizing: border-box;
}

/* Cercles décoratifs en arrière-plan (optionnel, style moderne) */
.background-blob-1 {
  position: absolute;
  top: -10%;
  left: -10%;
  width: 40vw;
  height: 40vw;
  background: radial-gradient(circle, rgba(0, 86, 179, 0.05) 0%, transparent 70%);
  border-radius: 50%;
  pointer-events: none;
}

.background-blob-2 {
  position: absolute;
  bottom: -10%;
  right: -10%;
  width: 50vw;
  height: 50vw;
  background: radial-gradient(circle, rgba(0, 75, 135, 0.04) 0%, transparent 70%);
  border-radius: 50%;
  pointer-events: none;
}
</style>
