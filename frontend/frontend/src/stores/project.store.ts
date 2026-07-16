import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { projectService } from "@/service/project.service.ts";
import { useAsync } from "@/composable/useAsync.ts";
import {createEmptyProjectForm, type Project, type ProjectRequest} from "@/types/project.types.ts";

export const useProjectStore = defineStore('project', () => {
  // L'état réactif principal de nos projets
  const projects = ref<Project[]>([]);

  // Historique pour l'Undo/Redo
  const undoStack = ref<string[]>([]);
  const redoStack = ref<string[]>([]);

  const canUndo = computed(() => undoStack.value.length > 0);
  const canRedo = computed(() => redoStack.value.length > 0);


  // 1. Pour la récupération (GET)
  const {
    loading: isLoading,
    error: fetchError,
    execute: fetchProjectsApi
  } = useAsync(() => projectService.getAll());

  // 2. Pour la création (POST)
  const {
    loading: isCreating,
    error: createError,
    execute: createProjectApi
  } = useAsync((dto: ProjectRequest) => projectService.createProject(dto));

  // 3. Pour la mise à jour (PUT)
  /*const {
    loading: isUpdating,
    error: updateError,
    execute: updateProjectApi
  } = useAsync((id: string, dto: ProjectRequest) => projectService.update(id, dto)); */


  // ==========================================
  //            MÉTHODES / ACTIONS
  // ==========================================

  async function loadProjects() {
    const data = await fetchProjectsApi();
    if (data) {
      projects.value = data;
    }
  }

  async function addProject(dto: ProjectRequest): Promise<Project | null> {
    saveSnapshot(); // On prépare l'annulation
    const newProject = await createProjectApi(dto);

    if (newProject) {
      projects.value.push(newProject);
      return newProject;
    } else {
      undoStack.value.pop(); // Annule le snapshot si l'API a échoué
      return null;
    }
  }

  /* async function updateProject(id: string, dto: ProjectRequest): Promise<Project | null> {
    saveSnapshot();
    const updatedProject = await updateProjectApi(id, dto);

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
  }*/

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
    // États et Données
    projects,

    // États de chargements (distincts et précis !)
    isLoading,
    isCreating,

    // Erreurs réseau
    fetchError,
    createError,

    // Undo / Redo
    canUndo,
    canRedo,
    undo,
    redo,

    // Actions métiers
    loadProjects,
    addProject,
    deleteLocalTask
  };
});
