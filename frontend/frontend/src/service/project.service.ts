import api from './api';
import {ProjectSchemaList, type Project } from '@/types/project.types.ts';

export const projectService = {
  async getAll(): Promise<Project[]>{
    const response = await api.get('/api/project')
    return ProjectSchemaList.parse(response.data);
  }
}
