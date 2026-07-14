import { z } from "zod";

// types/user.types.ts

// Le User officiel (ce que l'API renvoie)
export interface User {
  id: string;
  name: string; // Plus besoin de "?" s'il est toujours présent
  email: string;
}
export interface RegisterPayload extends Omit<User, 'id'> {
  password: string;
}

// 1. Schéma pour valider un utilisateur qui vient de l'API (sans mot de passe)
export const UserSchema: z.ZodType<User> = z.object({
  id: z.string().uuid(),
  name: z.string().min(1, "Le nom est requis"),
  email: z.string().email().refine((val) => val.endsWith("@arkea.com"), {
    message: "L'adresse doit appartenir au domaine @arkea.com"
  })
});

// 2. Schéma pour valider l'inscription (avec mot de passe obligatoire, mais sans ID)
export const RegisterSchema: z.ZodType<RegisterPayload> = z.object({
  name: z.string().min(1, "Le nom est requis"),
  email: z.string().email().refine((val) => val.endsWith("@arkea.com"), {
    message: "L'adresse doit appartenir au domaine @arkea.com"
  }),
  password: z.string().min(8, "Le mot de passe doit faire au moins 8 caractères")
});

// Une factory pour générer l'état initial sans dupliquer le typage
export const createEmptyRegisterPayload = (): RegisterPayload => ({
  name: "",
  email: "",
  password: ""
});
