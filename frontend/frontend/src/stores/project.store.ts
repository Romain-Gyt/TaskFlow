import { useProjects } from "@/composable/useProjects.ts";
import { defineStore } from "pinia";
import type { Project } from "@/types/project.types.ts";
import { computed, ref } from "vue";

export const useProjectStore = defineStore('project', () => {
  const { projects: apiProject, fetchProjects, loading, error } = useProjects();

  const projects = ref<Project[]>([]);
  const internalLoading = ref(false);

  const undoStack = ref<string[]>([]);
  const redoStack = ref<string[]>([]);

  const canUndo = computed(() => undoStack.value.length > 0);
  const canRedo = computed(() => redoStack.value.length > 0);

  async function loadProjects(){
    internalLoading.value = true;
    await fetchProjects();
    projects.value = JSON.parse(JSON.stringify(apiProject.value));
    internalLoading.value = false;
  }

  function saveSnapshot(){
    undoStack.value.push(JSON.stringify(projects.value));
    redoStack.value = [];
  }

  function deleteLocalTask(projectId: string, taskId: string){
    saveSnapshot();
    const project = projects.value.find(p => p.id === projectId);
    if (project) {
      project.tasks = project.tasks.filter(task => task.id !== taskId);
    }
  }

  function undo(){
    if (undoStack.value.length === 0) return;

    redoStack.value.push(JSON.stringify(projects.value));

    const previousState = undoStack.value.pop()!;
    projects.value = JSON.parse(previousState);
  }

  function redo(){
    if (redoStack.value.length === 0) return;

    undoStack.value.push(JSON.stringify(projects.value));

    const nextState = redoStack.value.pop()!;

    projects.value = JSON.parse(nextState);
  }

  return {
    projects,
    isLoading: computed(() => loading.value || internalLoading.value),
    error,
    canUndo,
    canRedo,
    loadProjects,
    deleteLocalTask,
    undo,
    redo
  };
});
