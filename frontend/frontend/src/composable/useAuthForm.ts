import { ref } from "vue";
import { LoginPayloadSchema, createEmptyLoginForm, type LoginPayload } from "@/types/user.type.ts";
import { useFormValidation } from "@/composable/useFormValidation.ts";
import { useAsync } from "@/composable/useAsync.ts"; // Ton nouveau composable !
import { authService } from "@/service/auth.service.ts";

export function useAuthForm() {
  const { errors: validationErrors, validate, clearErrors } = useFormValidation(LoginPayloadSchema);
  const loginPayload = ref<LoginPayload>(createEmptyLoginForm());

  const { loading: isSubmitting, error: apiError, execute: runLogin } = useAsync(authService.login);

  const handleLogin = async (): Promise<boolean> => {
    clearErrors();

    const isValid =  validate(loginPayload.value);
    if (!isValid) return false;

    const result = await runLogin(loginPayload.value);
    return !!result;
  };

  return {
    loginPayload,
    validationErrors,
    apiError,
    isSubmitting,
    handleLogin,
  };
}
