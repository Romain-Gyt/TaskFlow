import { ref, readonly } from 'vue';
import { projectService } from '@/service/project.service.ts';
import type { Project } from '@/types/project.types.ts';

export function useProjects(){
  const projects = ref<Project[]>([]);
  const loading = ref(false);
  const error = ref<String | null>(null);

  async function fetchProjects(){
    loading.value = true;
    error.value = null;
    try {
      projects.value = await projectService.getAll();
    } catch (err: any){
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  }
  return {
    projects: readonly(projects),
    loading: readonly(loading),
    error: readonly(error),
    fetchProjects
  }
}
