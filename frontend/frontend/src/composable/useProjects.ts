import { ref, readonly } from 'vue';
import { projectService } from '@/service/project.service.ts';
import type { Project } from '@/types/project.types.ts';
import {useAsync} from "@/composable/useAsync.ts";

export function useProjects(){
  const {
    data: projects,
    loading,
    error,
    execute: fetchProjects
  } = useAsync<Project[],[]>(() => projectService.getAll());

  return {
    projects,
    loading,
    error,
    fetchProjects
  }
}
