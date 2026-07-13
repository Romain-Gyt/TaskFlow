import { z } from 'zod';
import { TaskSchema, type Task } from './task.types';

export interface Project {
  id: string;
  name: string;
  description: string;
  tasks: Task[];
}

export const ProjectSchema: z.ZodType<Project> = z.object({
  id: z.string().uuid("Project ID must be a valid UUID"),
  name: z.string().min(1, "Name is required").max(255, "Name must be less than 255 characters"),
  description: z.string().min(1, "Description is required").max(500, "Description must be less than 500 characters"),
  tasks: z.array(TaskSchema)
});

export const ProjectSchemaList = z.array(ProjectSchema);
