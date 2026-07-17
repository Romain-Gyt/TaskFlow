<script setup lang="ts">
import { useProjectStore } from "@/stores/project.store.ts";
import { storeToRefs } from "pinia";
import {createEmptyProjectForm, type ProjectRequest} from "@/types/project.types.ts";
import {computed, onMounted, ref, watch} from "vue";
import TaskProjectManager from "@/components/organisms/TaskProjectManager.vue";
import {useRoute, useRouter} from "vue-router";

const store = useProjectStore();
const route = useRoute();
const router = useRouter();
const { isSingleLoading, isCreating, isUpdating, createError, updateError } = storeToRefs(store);
const { getProjectById,addProject, updateProject } = store;

const project = ref<ProjectRequest>(createEmptyProjectForm());

const projectId = computed(() => route.params.id as string | undefined);
const isEditMode = computed(() => !!projectId.value);

watch(isEditMode, (editMode) => {
  if (!editMode) {
    project.value = createEmptyProjectForm();
  }
});

onMounted(async () => {
  if (isEditMode.value && projectId.value) {
    const existingProject = await getProjectById(projectId.value);
    console.log(existingProject);
    if (existingProject) {
      project.value = {
        name: existingProject.name,
        description: existingProject.description,
        tasks: existingProject.tasks,
      };
    } else {
      await router.push({ name: "kanban-board" });
    }
  }
});
const handleSubmit = async () => {
  let success = false;

  if (isEditMode.value && projectId.value) {
    const updated = await updateProject(projectId.value, project.value);
    success = !!updated;
  } else {
    const created = await addProject(project.value);
    success = !!created;
  }
  if (success) {
    project.value = createEmptyProjectForm();
    await router.push({name: "kanban-board"});
  }
}

</script>

<template>
  <div class="form-container">
    <div class="form-card">
      <header class="form-header">
        <p v-if="isSingleLoading"> Le projet est en cours de chargement....</p>
        <h1>{{ isEditMode ? "Modifier le projet" : "Nouveau projet"}}</h1>
        <p>{{ isEditMode ? "Modifiez" : "Créez"}} un nouvel espace de travail pour organiser vos tâches.</p>
      </header>

      <form @submit.prevent="handleSubmit" class="project-form">
        <!-- Champ Nom -->
        <div class="form-group">
          <label for="name">Nom du projet <span class="required">*</span></label>
          <input
            id="name"
            v-model="project.name"
            type="text"
            placeholder="Ex: Refonte du site vitrine"
            required
            :disabled="isEditMode? isUpdating : isCreating"
            autocomplete="off"
          />
        </div>

        <!-- Champ Description -->
        <div class="form-group">
          <label for="description">Description <span class="required">*</span></label>
          <textarea
            id="description"
            v-model="project.description"
            placeholder="Décrivez brièvement les objectifs de ce projet..."
            required
            :disabled="isEditMode? isUpdating : isCreating"
            rows="4"
          ></textarea>
        </div>

        <!-- 💡 Tu injectes l'organisme directement en le bindant sur les tâches du projet local ! -->
        <TaskProjectManager
          v-model="project.tasks"
          :disabled="isEditMode? isUpdating : isCreating"
        />

        <!-- Message d'erreur API -->
        <div v-if="createError" class="error-banner" role="alert">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="12"></line><line x1="12" y1="16" x2="12.01" y2="16"></line></svg>
          <span>{{  isEditMode ? updateError :createError }}</span>
        </div>

        <!-- Actions -->
        <div class="form-actions">
          <button
            type="button"
            class="btn-secondary"
            @click="router.push({ name: 'kanban-board' })"
            :disabled="isEditMode? isUpdating : isCreating"
          >
            Annuler
          </button>

          <button
            type="submit"
            class="btn-primary"
            :disabled="isCreating || !project.name || !project.description"
          >
            <span v-if="isCreating" class="spinner"></span>
            <span>{{ isEditMode ? 'Modifier' : 'Créer le projet' }}</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* Conteneur principal centré */
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 80px); /* Ajuste selon la hauteur de ton header global */
  padding: 2rem 1rem;
  background-color: #f8fafc; /* fond gris ardoise très clair */
}

/* Carte du formulaire */
.form-card {
  width: 100%;
  max-width: 560px;
  background: #ffffff;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
  border: 1px solid #e2e8f0;
}

/* En-tête */
.form-header {
  margin-bottom: 2rem;
}

.form-header h1 {
  font-size: 1.75rem;
  font-weight: 700;
  color: #0f172a; /* Slate 900 */
  margin-bottom: 0.5rem;
}

.form-header p {
  font-size: 0.95rem;
  color: #64748b; /* Slate 500 */
}

/* Structure du formulaire */
.project-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* Groupe de champs */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-size: 0.875rem;
  font-weight: 600;
  color: #334155; /* Slate 700 */
}

.required {
  color: #ef4444; /* Rouge */
}

/* Inputs & Textarea */
input[type="text"],
textarea {
  width: 100%;
  padding: 0.75rem 1rem;
  font-size: 0.95rem;
  font-family: inherit;
  color: #0f172a;
  background-color: #ffffff;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
  transition: all 0.2s ease;
  outline: none;
}

input[type="text"]:focus,
textarea:focus {
  border-color: #6366f1; /* Indigo */
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.15);
}

input:disabled,
textarea:disabled {
  background-color: #f1f5f9;
  color: #94a3b8;
  cursor: not-allowed;
}

/* Bannière d'erreur */
.error-banner {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem;
  background-color: #fef2f2;
  border: 1px solid #fca5a5;
  border-radius: 8px;
  color: #991b1b;
  font-size: 0.9rem;
}

.error-banner svg {
  flex-shrink: 0;
}

/* Boutons et Actions */
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 1rem;
}

button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  font-size: 0.95rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  border: 1px solid transparent;
}

.btn-secondary {
  background-color: #ffffff;
  border-color: #cbd5e1;
  color: #475569;
}

.btn-secondary:hover:not(:disabled) {
  background-color: #f8fafc;
  border-color: #94a3b8;
}

.btn-primary {
  background-color: #6366f1; /* Indigo primary */
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background-color: #4f46e5;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Spinner de chargement */
.spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: #ffffff;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
