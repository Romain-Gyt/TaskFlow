<script setup lang="ts">
import FormField from "@/components/molecules/FormField.vue";
import BaseButton from "@/components/atoms/BaseButton.vue";
import { useAuthForm } from "@/composable/useAuthForm";
import { useRouter } from "vue-router";

const router = useRouter();
const {loginPayload,validationErrors,apiError,isSubmitting,handleLogin } = useAuthForm();

const handleSubmit = async () => {
  const success = await handleLogin();
  if (success) {
    await router.push("/dashboard");
  }
};
</script>

<template>
  <div class="login-card">
    <div class="login-header">
      <div class="logo-badge">A</div>
      <h2>Socle Arkéa</h2>
      <p>Accédez à votre espace de gestion Kanban</p>
    </div>

    <div v-if="apiError" class="alert-error" aria-live="assertive">
      ⚠️ {{ apiError }}
    </div>

    <form @submit.prevent="handleSubmit" class="form-layout" novalidate>
      <FormField
        v-model="loginPayload.email"
        label="Adresse email"
        :required="true"
        :error="validationErrors.email"
        :disabled="isSubmitting"
        type="email"
        placeholder="jean.dupont@arkea.com"
        autocomplete="username"
      />

      <FormField
        v-model="loginPayload.password"
        label="Mot de passe"
        :required="true"
        :error="validationErrors.password"
        :disabled="isSubmitting"
        type="password"
        placeholder="••••••••"
        autocomplete="current-password"
      />

      <div class="form-options">
        <label class="remember-me">
          <input type="checkbox" :disabled="isSubmitting" /> Se souvenir de moi
        </label>
        <a href="#" class="forgot-link">Mot de passe oublié ?</a>
      </div>

      <BaseButton
        type="submit"
        :label="isSubmitting ? 'Connexion...' : 'Se connecter'"
        variant="primary"
        :disabled="isSubmitting"
      />
    </form>
  </div>
</template>

<style scoped>
/* Le CSS reste identique */
.login-card { background: #ffffff; padding: 2.5rem; border-radius: 12px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05); width: 100%; max-width: 420px; box-sizing: border-box; }
.login-header { text-align: center; margin-bottom: 2rem; }
.logo-badge { width: 48px; height: 48px; background-color: #0056b3; color: #ffffff; font-size: 1.5rem; font-weight: bold; display: inline-flex; align-items: center; justify-content: center; border-radius: 8px; margin-bottom: 1rem; }
.login-header h2 { margin: 0; font-size: 1.5rem; color: #1e293b; font-weight: 700; }
.login-header p { margin: 0.5rem 0 0 0; font-size: 0.875rem; color: #64748b; }
.form-layout { display: flex; flex-direction: column; }
.form-options { display: flex; justify-content: space-between; align-items: center; font-size: 0.85rem; margin-bottom: 1.5rem; }
.remember-me { display: flex; align-items: center; gap: 0.35rem; color: #475569; cursor: pointer; }
.forgot-link { color: #0056b3; text-decoration: none; font-weight: 500; }
.forgot-link:hover { text-decoration: underline; }
.alert-error { background-color: #fef2f2; border: 1px solid #fee2e2; color: #dc2626; padding: 0.75rem 1rem; border-radius: 6px; font-size: 0.875rem; margin-bottom: 1.25rem; font-weight: 500; }
</style>
