
import api from "@/service/api.ts";
import type {LoginPayload, LoginResponse} from "@/types/user.type.ts";


export const authService = {
  async login(payload: LoginPayload): Promise<LoginResponse> {
    const response = await api.post("/api/auth/login", payload);
    return response.data;
  }
}
