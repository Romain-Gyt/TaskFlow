import { z } from 'zod';

export const ProjectSchema = z.object({
  id: z.string().uuid(),
  name: z.string().min(1, "Name is required").max(255, "Name must be less than 255 characters"),
  description: z.string().min(1, "Description is required").max(500, "Description must be less than 500 characters")
});

export const ProjectSchemaList = z.array(ProjectSchema);

export type Project = z.infer<typeof ProjectSchema>;
