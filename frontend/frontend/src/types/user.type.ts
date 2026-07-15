import { z } from "zod";

// ==========================================
// 1. TYPES TYPESCRIPT (Source de vérité)
// ==========================================

export interface User {
  id: string;
  email: string;
}

export interface RegisterPayload extends Omit<User, 'id'> {
  password: string;
}

export type LoginPayload = Pick<RegisterPayload, 'email' | 'password'>;


// ==========================================
// 2. SCHÉMAS ZOD (Validation)
// ==========================================

// Le schéma de base pour un User
export const UserSchema = z.object({
  id: z.string().uuid("Format d'identifiant invalide"),
  email: z.string()
    .email("Format d'adresse email invalide")
    .refine((val) => val.endsWith("@arkea.com"), {
      message: "L'adresse doit appartenir au domaine @arkea.com"
    })
});

// RegisterSchema = On enlève l'id de UserSchema + on ajoute les règles du password
export const RegisterSchema = UserSchema.omit({ id: true }).extend({
  password: z.string().min(8, "Le mot de passe doit faire au moins 8 caractères")
});

// LoginSchema = On garde uniquement l'email de UserSchema + on ajoute la règle du password de connexion
export const LoginSchema = UserSchema.pick({ email: true }).extend({
  password: z.string().min(1, "Le mot de passe est requis")
});


// ==========================================
// 3. HELPERS D'INITIALISATION
// ==========================================

export const createEmptyLoginForm = (): LoginPayload => ({
  email: "",
  password: ""
});

export const createEmptyRegisterForm = (): RegisterPayload => ({
  email: "",
  password: ""
});
