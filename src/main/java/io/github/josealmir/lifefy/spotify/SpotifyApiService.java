package io.github.josealmir.lifefy.spotify;

import io.github.josealmir.lifefy.spotify.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotifyApiService {
    @Autowired
    private SpotifyApiRepository api;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.secret}")
    private String secret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @Value("${spotify.token-url}")
    private String tokenUrl;

    public boolean isValidToken(String accessToken) {
        try {
            Response<User> userResponse = api.getCurrentUserProfile("Bearer ".concat(accessToken)).execute();
            return userResponse.code() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    public String getSpotifyAuthorizeUrl(String state) {
        String TEMPLATE = "https://accounts.spotify.com/authorize?response_type=%s&client_id=%s&scope=%s&redirect_uri=%s&state=%s";
        String SCOPE = "user-read-private user-read-email playlist-read-private playlist-modify-private playlist-modify-public user-top-read user-follow-read";

        return String.format(TEMPLATE, "code", clientId, SCOPE, redirectUri, state);
    }


    public User getUser(String accessToken) {
        try {
            Response<User> userResponse = api.getCurrentUserProfile("Bearer ".concat(accessToken)).execute();
            return userResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PaginationResults<Artist> getUserTopArtists(String accessToken) {
        try {
            Response<PaginationResults<Artist>> topArtistsResponse = api.getUserTopArtists("Bearer ".concat(accessToken), "short_term").execute();
            return topArtistsResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PaginationResults<Track> getUserTopTracks(String accessToken) {
        try {
            Response<PaginationResults<Track>> topTrackResponse = api.getUserTopTracks("Bearer ".concat(accessToken), "short_term").execute();
            return topTrackResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Recommendations getRecommendations(String accessToken) {
        PaginationResults<Artist> topArtists = getUserTopArtists(accessToken);
        List<Artist> artists = topArtists.getItems();
        String artistSeed = getArtistsSeed(artists);
        String genreSeed = getGenreSeed(artists);

        try {
            Response<Recommendations> recommendationsResponse = api.getRecommendations("Bearer ".concat(accessToken), genreSeed, artistSeed).execute();
            return recommendationsResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Playlist createPlaylist(String accessToken, String name) {
        try {
            User user = getUser(accessToken);
            Map<String, Object> body = new HashMap<>();
            body.put("name", name);
            body.put("description", "Uma playlist gerada pelo Lifefy");
            Response<Playlist> playlistResponse = api.postPlaylist("Bearer ".concat(accessToken), user.getId(), body).execute();
//            System.out.println(playlistResponse.code());
//            System.out.println(playlistResponse.message());
//            System.out.println(playlistResponse.errorBody().string());
            return playlistResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTracksToPlaylist(String accessToken, String ids, String playlistId) {
        try {
            UrisRequestBody body = new UrisRequestBody();
            body.setUris(List.of(ids.split(",")));
            body.setPosition(0);
            api.addTracksToPlaylist("Bearer ".concat(accessToken), playlistId, body).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getArtistsSeed(List<Artist> artists) {
        Collections.shuffle(artists);
        return artists.subList(0, 2).stream().map((Artist::getId)).collect(Collectors.joining(","));
    }

    private String getGenreSeed(List<Artist> artists) {
        List<String[]> genresByArtist = artists.stream().map(Artist::getGenres).toList();
        List<String> allGenres = new ArrayList<>();

        for (var genreArtist : genresByArtist) {
            allGenres.addAll(List.of(genreArtist));
        }

        Collections.shuffle(allGenres);
        allGenres = allGenres.subList(0, 3);

        return String.join(",", new HashSet<>(allGenres));
    }

    public Artist getArtist(String accessToken, String id) {
        try {
            Response<Artist> artistResponse = api.getArtist("Bearer ".concat(accessToken), id).execute();
            return artistResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Artists getSeveralArtists(String accessToken, String ids) {
        try {
            Response<Artists> artistResponse = api.getSeveralArtists("Bearer ".concat(accessToken), ids).execute();
            return artistResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Album getAlbum(String accessToken, String id) {
        try {
            Response<Album> albumResponse = api.getAlbum("Bearer ".concat(accessToken), id).execute();
            return albumResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Albums getSeveralAlbums(String accessToken, String id) {
        try {
            Response<Albums> albumResponse = api.getSeveralAlbums("Bearer ".concat(accessToken), id).execute();
            return albumResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Track getTrack(String accessToken, String id) {
        try {
            Response<Track> trackResponse = api.getTrack("Bearer ".concat(accessToken), id).execute();
            return trackResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Tracks getSeveralTracks(String accessToken, String id) {
        try {
            Response<Tracks> trackResponse = api.getSeveralTracks("Bearer ".concat(accessToken), id).execute();
            return trackResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpotifyToken getUserToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String tokenPart = clientId + ":" + secret;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(tokenPart.getBytes())));
        HttpEntity<String> httpEntity = new HttpEntity<>(String.format("code=%s&redirect_uri=%s&grant_type=authorization_code", code, redirectUri), headers);

        try {
            return restTemplate.postForObject(tokenUrl, httpEntity, SpotifyToken.class);
        } catch (RestClientException ex) {
            throw new RuntimeException(ex);
        }
    }
}
