package io.github.josealmir.lifefy.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
    public void setCookie(CookieType type, String value, HttpServletResponse response) {
        Cookie cookie = null;

        switch (type) {
            case AUTH_STATE -> {
                cookie = new Cookie(type.getKey(), value);
                cookie.setHttpOnly(true);
            }
            case AUTH_ACCESS_TOKEN -> {
                cookie = new Cookie(type.getKey(), value);
                cookie.setMaxAge(3600);
                cookie.setHttpOnly(true);
            }
        }

        if (cookie != null) {
            response.addCookie(cookie);
        }
    }

    public enum CookieType {
        AUTH_STATE("spotify_auth_state"),
        AUTH_ACCESS_TOKEN("spotify_auth_access_token");

        private final String key;

        CookieType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
