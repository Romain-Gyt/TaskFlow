import {
  createEmptyRegisterPayload,
  type RegisterPayload,
  RegisterSchema
} from "@/types/user.type.ts";
import {ref} from "vue";
import {useFormValidation} from "@/composable/useFormValidation.ts";

export function useRegisterForm(){
  const user = ref<RegisterPayload>(createEmptyRegisterPayload());
  const isLoading = ref(false);

  // On initialise le validateur avec le schéma d'inscription
  const { errors, validate, clearErrors } = useFormValidation(RegisterSchema);

  const register = async () => {
    // On valide les refs locales face au schéma
    const { isValid, data } = validate({
      user
    });

    if (!isValid || !data) return false;

    isLoading.value = true;
    try {
      // Ton appel API d'inscription ici avec "data" qui est 100% typé !
      //await api.post('/register', data);
      return true;
    } catch (err) {
      console.error(err);
      return false;
    } finally {
      isLoading.value = false;
    }
  };

  return {
    user,
    errors, // Ces erreurs proviennent directement de useFormValidation
    isLoading,
    register,
    clearErrors
  };
}

