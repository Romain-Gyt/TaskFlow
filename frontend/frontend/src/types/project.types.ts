import { z } from 'zod';
import { TaskSchema} from './task.types'; // Importe les schémas Zod des tâches

// ==========================================
// 1. SCHÉMAS DE VALIDATION ZOD (Source unique de vérité)
// ==========================================

// Le schéma complet retourné par l'API (Response)
export const ProjectSchema = z.object({
  id: z.string().uuid("Project ID must be a valid UUID"),
  name: z.string().min(1, "Name is required").max(255, "Name must be less than 255 characters"),
  description: z.string().min(1, "Description is required").max(500, "Description must be less than 500 characters"),
  tasks: z.array(TaskSchema)
});

// Le schéma de liste
export const ProjectSchemaList = z.array(ProjectSchema);

// Le schéma de création envoyé à l'API (Request)
export const ProjectRequestSchema = z.object({
  name: z.string().min(1, "Name is required").max(255, "Name must be less than 255 characters"),
  description: z.string().min(1, "Description is required").max(500, "Description must be less than 500 characters"),
  tasks: z.array(TaskSchema).default([]) // Utilise le DTO de tâche sans ID dédié à la création d'un projet
});


// ==========================================
// 2. TYPES TYPESCRIPT INFÉRÉS (Générés automatiquement)
// ==========================================

// Type équivalent à ton ancienne interface Project (avec ID)
export type Project = z.infer<typeof ProjectSchema>;

// Type pour la requête de création (sans ID)
export type ProjectRequest = z.infer<typeof ProjectRequestSchema>;

//Helper

export const createEmptyProjectForm = (): ProjectRequest => ({
  name:"",
  description: "",
  tasks: [],
})
