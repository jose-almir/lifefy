package io.github.josealmir.lifefy.controller;

import io.github.josealmir.lifefy.spotify.SpotifyApiService;
import io.github.josealmir.lifefy.spotify.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private SpotifyApiService apiService;

    @GetMapping("/")
    public ModelAndView index(
            @CookieValue(name = "spotify_auth_access_token", required = false) String accessToken
    ) {
        if (accessToken != null) {
            return new ModelAndView("redirect:/me");
        }

        return new ModelAndView("index");
    }

    @GetMapping("/me")
    public ModelAndView timeline(
            @CookieValue(name = "spotify_auth_access_token", required = false) String accessToken,
            @RequestParam(value = "birthdate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
            @RequestParam(value = "created", required = false) String created,
            Model model
    ) {
        if (accessToken != null) {
            Recommendations recommendations = apiService.getRecommendations(accessToken);
            User user = apiService.getUser(accessToken);
            List<Artist> topArtists = apiService.getUserTopArtists(accessToken).getItems();
            List<Track> topTracks = apiService.getUserTopTracks(accessToken).getItems();
            model.addAttribute("user", user);
            model.addAttribute("top_artists", topArtists);
            model.addAttribute("top_tracks", topTracks);
            model.addAttribute("recommendations", recommendations.getTracks());
            model.addAttribute("recommendations_uris", recommendations.getTracksUri());
//            if (birthdate != null) {
//                //
//                model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            } else {
//                model.addAttribute("birthdate", "");
//            }
            return new ModelAndView("me");
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @PostMapping("/playlist")
    public ModelAndView playlist(
            @CookieValue(name = "spotify_auth_access_token", required = false) String accessToken,
            @ModelAttribute UrisBody uris
    ) {
        Playlist playlist = apiService.createPlaylist(accessToken, "Recomendações da Lifefy");
        apiService.addTracksToPlaylist(accessToken, uris.getUris(), playlist.getId());

        return new ModelAndView("redirect:/me?created=".concat(playlist.getExternalUrls().getSpotify()));
    }
}
