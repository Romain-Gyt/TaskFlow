package com.arkea.taskflow.utils;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private CookieUtils() {}

    private static final int MAX_AGE = 3600; // 1 heure en secondes
    private static final int EXPIRATION = 0;
    private static final String COOKIE_AUTH = "AUTH-TOKEN";

    // On utilise directement des primitifs clairs pour éviter les négations (!) piégeuses
    private static final boolean HTTP_ONLY = true;
    private static final boolean SECURE = false; // 💡 false pour HTTP (local), true pour HTTPS (prod)
    private static final String SAME_SITE_LAX = "Lax";

    public static ResponseCookie addAuthCookie(String token){
        return ResponseCookie.from(COOKIE_AUTH, token)
                .httpOnly(HTTP_ONLY)
                .secure(SECURE)
                .path("/")
                .maxAge(MAX_AGE) // 💡 Indique au navigateur de le garder 3600 secondes
                .sameSite(SAME_SITE_LAX)
                .build();
    }

    public static ResponseCookie clearAuthCookie(){
        return ResponseCookie.from(COOKIE_AUTH, "")
                .httpOnly(HTTP_ONLY)
                .secure(SECURE)
                .path("/")
                .maxAge(EXPIRATION)
                .sameSite(SAME_SITE_LAX)
                .build();
    }
}