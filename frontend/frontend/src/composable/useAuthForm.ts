import { ref } from "vue";

export function useAuthForm() {
  const email = ref("");
  const password = ref("");

  const emailError = ref("");
  const passwordError = ref("");
  const globalError = ref("");
  const isLoading = ref(false);

  const validateForm = (): boolean => {
    let isValid = true;

    if (!email.value.includes("@arkea.com")) {
      emailError.value = "Veuillez utiliser votre adresse email professionnelle Arkéa.";
      isValid = false;
    } else {
      emailError.value = "";
    }

    if (password.value.length < 8) {
      passwordError.value = "Le mot de passe doit contenir au moins 8 caractères.";
      isValid = false;
    } else {
      passwordError.value = "";
    }

    return isValid;
  };

  const login = async () => {
    globalError.value = "";
    if (!validateForm()) return;

    isLoading.value = true;
    try {
      // Simulation API (À remplacer plus tard par ton vrai service/store d'authentification)
      await new Promise((resolve, reject) => {
        setTimeout(() => {
          if (email.value === "admin@arkea.com" && password.value === "password123") {
            resolve(true);
          } else {
            reject(new Error("Identifiants incorrects. Veuillez réessayer."));
          }
        }, 1500);
      });

      return true; // Connexion réussie
    } catch (err: any) {
      globalError.value = err.message || "Une erreur est survenue.";
      return false; // Échec
    } finally {
      isLoading.value = false;
    }
  };

  return {
    email,
    password,
    emailError,
    passwordError,
    globalError,
    isLoading,
    login
  };
}
