import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { projectService } from "@/service/project.service.ts";
import { useAsync } from "@/composable/useAsync.ts";
import {createEmptyProjectForm, type Project, type ProjectRequest} from "@/types/project.types.ts";

export const useProjectStore = defineStore('project', () => {
  const projects = ref<Project[]>([]);
  const undoStack = ref<string[]>([]);
  const redoStack = ref<string[]>([]);

  const canUndo = computed(() => undoStack.value.length > 0);
  const canRedo = computed(() => redoStack.value.length > 0);

  // 1. Pour la récupération de TOUS les projets (GET)
  const {
    loading: isLoading,
    error: fetchError,
    execute: fetchProjectsApi
  } = useAsync(() => projectService.getAll());

  // 💡 1b. NOUVEAU : Pour la récupération d'un SEUL projet (GET /{id})
  const {
    loading: isSingleLoading,
    error: singleFetchError,
    execute: fetchSingleProjectApi
  } = useAsync((id: string) => projectService.getProjectById(id)); // Assure-toi que cette méthode existe dans ton projectService

  // 2. Pour la création (POST)
  const {
    loading: isCreating,
    error: createError,
    execute: createProjectApi
  } = useAsync((dto: ProjectRequest) => projectService.createProject(dto));

  // 💡 3. UNCOMMENT & FIX : Pour la mise à jour (PUT)
  const {
    loading: isUpdating,
    error: updateError,
    execute: updateProjectApi
  } = useAsync((id: string, dto: ProjectRequest) => projectService.updateProject(id, dto));
  // Note : useAsync prend généralement un seul argument, on passe donc un objet { id, dto }

  // 4. Pour la SUPPRESSION (DELETE)
  const {
    loading: isDeleting,
    error: deleteError,
    execute: deleteProjectApi
  } = useAsync((id: string) => projectService.deleteProject(id));


  // ==========================================
  //            MÉTHODES / ACTIONS
  // ==========================================

  async function loadProjects() {
    const data = await fetchProjectsApi();
    if (data) {
      projects.value = data;
    }
  }

  async function getProjectById(id: string): Promise<Project | null> {
    // 1. Optionnel : On regarde si on l'a déjà en local pour aller vite
    const localProject = projects.value.find(p => p.id === id);
    if (localProject) return localProject;

    // 2. Sinon, on interroge l'API
    return await fetchSingleProjectApi(id);
  }

  async function addProject(dto: ProjectRequest): Promise<Project | null> {
    saveSnapshot();
    const newProject = await createProjectApi(dto);

    if (newProject) {
      projects.value.push(newProject);
      return newProject;
    } else {
      undoStack.value.pop();
      return null;
    }
  }

  // 💡 ACTION DE MISE À JOUR DÉCOMMENTÉE
  async function updateProject(id: string, dto: ProjectRequest): Promise<Project | null> {
    saveSnapshot();
    const updatedProject = await updateProjectApi(id,dto);

    if (updatedProject) {
      const index = projects.value.findIndex(p => p.id === id);
      if (index !== -1) {
        projects.value[index] = updatedProject;
      }
      return updatedProject;
    } else {
      undoStack.value.pop();
      return null;
    }
  }

  async function deleteProject(id: string): Promise<void> {
    saveSnapshot();
    await deleteProjectApi(id);
    if (!deleteError.value) {
      projects.value = projects.value.filter((project) => project.id !== id);
    } else {
      undoStack.value.pop();
    }
  }

  // --- HISTORIQUE (UNDO / REDO) ---
  function saveSnapshot() {
    undoStack.value.push(JSON.stringify(projects.value));
    redoStack.value = [];
  }

  function deleteLocalTask(projectId: string, taskId: string) {
    saveSnapshot();
    const project = projects.value.find(p => p.id === projectId);
    if (project) {
      project.tasks = project.tasks.filter(task => task.id !== taskId);
    }
  }

  function undo() {
    if (undoStack.value.length === 0) return;
    redoStack.value.push(JSON.stringify(projects.value));
    projects.value = JSON.parse(undoStack.value.pop()!);
  }

  function redo() {
    if (redoStack.value.length === 0) return;
    undoStack.value.push(JSON.stringify(projects.value));
    projects.value = JSON.parse(redoStack.value.pop()!);
  }

  return {
    projects,

    // États de chargements
    isLoading,
    isSingleLoading,
    isCreating,
    isUpdating,
    isDeleting,

    // Erreurs réseau
    fetchError,
    singleFetchError,
    createError,
    updateError,
    deleteError,

    // Undo / Redo
    canUndo,
    canRedo,
    undo,
    redo,

    // Actions métiers
    loadProjects,
    getProjectById, // 💡 Exposé pour ton composant !
    addProject,
    updateProject,  // 💡 Exposé pour ton composant !
    deleteLocalTask,
    deleteProject,
  };
});
