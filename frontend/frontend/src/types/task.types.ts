import  { z } from 'zod';


export interface Task{
  id: string;
  title: string;
  status: string
  projectId: string;
}

export const TaskSchema: z.ZodType<Task> = z.object({
  id: z.string().uuid(),
  title: z.string().min(1,"Name is required").max(255,"Name must be less than 255 characters"),
  status: z.enum(["A_FAIRE","EN_COURS","TERMINEE"]),
  projectId: z.string().uuid("Project ID must be a valid UUID")
})

export const TaskSchemaList = z.array(TaskSchema);


