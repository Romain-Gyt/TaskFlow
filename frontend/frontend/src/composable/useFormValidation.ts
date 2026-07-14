import { ref } from 'vue';
import type { Ref } from 'vue';
import type { ZodSchema, TypeOf } from 'zod';

export type FormErrors<T> = {
  [K in keyof T]?: string;
};


interface ValidationResult<T> {
  isValid: boolean;
  data: T | null;
}

export function useFormValidation<S extends ZodSchema>(schema: S) {
  type FormData = TypeOf<S>;

  const errors = ref<FormErrors<FormData>>({}) as Ref<FormErrors<FormData>>;

  const validate = (data: unknown): ValidationResult<FormData> => {
    const result = schema.safeParse(data);

    if (!result.success) {
      const formatted = result.error.format();
      const simpleErrors: FormErrors<FormData> = {};

      for (const key in formatted) {
        if (key !== '_errors') {
          const fieldError = (formatted as any)[key];
          if (fieldError && fieldError._errors && fieldError._errors.length > 0) {
            simpleErrors[key as keyof FormData] = fieldError._errors[0];
          }
        }
      }

      errors.value = simpleErrors;
      return { isValid: false, data: null };
    }

    errors.value = {};
    return { isValid: true, data: result.data as FormData };
  };

  const clearErrors = () => {
    errors.value = {};
  };

  return {
    errors,
    validate,
    clearErrors,
  };
}
