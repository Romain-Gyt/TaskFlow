import { z } from "zod";

// ==========================================
// 1. SCHÉMAS ZOD (Source de vérité absolue)
// ==========================================

export const UserSchema = z.object({
  id: z.string().uuid("Format d'identifiant invalide"),
  email: z.string()
    .email("Format d'adresse email invalide")
    .refine((val) => val.endsWith("@arkea.com"), {
      message: "L'adresse doit appartenir au domaine @arkea.com"
    })
});

// Payload strict pour l'API (Inscriptions)
export const RegisterPayloadSchema = UserSchema.omit({ id: true }).extend({
  password: z.string().min(8, "Le mot de passe doit faire au moins 8 caractères")
});

// Schéma complet pour le Formulaire UI (avec confirmation)
export const RegisterFormSchema = RegisterPayloadSchema.extend({
  confirmPassword: z.string().min(1, "La confirmation est requise")
}).refine((data) => data.password === data.confirmPassword, {
  message: "Les mots de passe ne correspondent pas",
  path: ["confirmPassword"]
});

// Payload strict pour l'API (Connexion)
export const LoginPayloadSchema = UserSchema.pick({ email: true }).extend({
  password: z.string().min(1, "Le mot de passe est requis")
});

// ==========================================
// 2. TYPES TYPESCRIPT (Générés automatiquement)
// ==========================================

export interface LoginResponse {
  message: string;
}

export type User = z.infer<typeof UserSchema>;
export type RegisterPayload = z.infer<typeof RegisterPayloadSchema>;
export type RegisterFormValues = z.infer<typeof RegisterFormSchema>;
export type LoginPayload = z.infer<typeof LoginPayloadSchema>;

// ==========================================
// 3. HELPERS D'INITIALISATION
// ==========================================

export const createEmptyLoginForm = (): LoginPayload => ({
  email: "",
  password: ""
});

export const createEmptyRegisterForm = (): RegisterFormValues => ({
  email: "",
  password: "",
  confirmPassword: ""
});
