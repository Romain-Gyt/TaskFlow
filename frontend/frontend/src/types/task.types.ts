import  { z } from 'zod';
import { ProjectSchema, type Project } from '@/types/project.types.ts';

export interface Task{
  id: string;
  title: string;
  project: Project;
}git

export const TaskSchema: z.ZodType<Task> = z.object({
  id: z.string().uuid(),
  title: z.string().min(1,"Name is required").max(255,"Name must be less than 255 characters"),
  project: ProjectSchema,
})

export const TaskSchemaList = z.array(TaskSchema);


