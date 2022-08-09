package io.github.josealmir.lifefy.controller;

import io.github.josealmir.lifefy.spotify.SpotifyApiService;
import io.github.josealmir.lifefy.spotify.models.SpotifyToken;
import io.github.josealmir.lifefy.util.CookieUtil;
import io.github.josealmir.lifefy.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SpotifyController {
    @Autowired
    private SpotifyApiService apiService;

    @Autowired
    private CookieUtil cookieUtil;

    @GetMapping("/login")
    public RedirectView login(HttpServletResponse response) {
        String state = RandomString.getAlphaNumericString(16);
        cookieUtil.setCookie(CookieUtil.CookieType.AUTH_STATE, state, response);
        String url = apiService.getSpotifyAuthorizeUrl(state);
        return new RedirectView(url);
    }

    @GetMapping("/callback")
    public ModelAndView callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @CookieValue(name = "spotify_auth_state") String storedState,
            HttpServletResponse response
    ) {
        if (!state.equals(storedState)) {
            return new ModelAndView("error");
        }

        SpotifyToken spotifyResponse = apiService.getUserToken(code);

        if (spotifyResponse != null) {
            cookieUtil.setCookie(CookieUtil.CookieType.AUTH_ACCESS_TOKEN, spotifyResponse.getAccessToken(), response);
            return new ModelAndView("redirect:/me");
        }

        return new ModelAndView("error");
    }
}
