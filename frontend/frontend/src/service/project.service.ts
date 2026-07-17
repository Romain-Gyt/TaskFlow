import api from './api';
import {
  ProjectSchemaList,
  type Project,
  ProjectSchema,
  type ProjectRequest
} from '@/types/project.types.ts';

export const projectService = {
  async getAll(): Promise<Project[]>{
    const response = await api.get('/api/projects')
    return ProjectSchemaList.parse(response.data);
  },
  async getProjectById(id: string): Promise<Project> {
    const response = await api.get('/api/projects/' + id);
    return ProjectSchema.parse(response.data);
  },
  async createProject(project: ProjectRequest): Promise<Project> {
    const response = await api.post('/api/projects', project);
    return ProjectSchema.parse(response.data);
  },
  async updateProject(id: string,project: ProjectRequest): Promise<Project> {
    const response = await api.put('/api/projects/' + id, project);
    return ProjectSchema.parse(response.data);
  },
  async deleteProject(projectId: string): Promise<void> {
    const response = await api.delete(`/api/projects/${projectId}`);
  }
}
