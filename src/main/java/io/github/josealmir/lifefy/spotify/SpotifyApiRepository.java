package io.github.josealmir.lifefy.spotify;

import io.github.josealmir.lifefy.spotify.models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface SpotifyApiRepository {
    @GET("me")
    Call<User> getCurrentUserProfile(@Header("Authorization") String authorization);

    @GET("me/top/artists?limit=5")
    Call<PaginationResults<Artist>> getUserTopArtists(
            @Header("Authorization") String authorization,
            @Query("time_range") String timeRange
    );

    @GET("me/top/tracks?limit=5")
    Call<PaginationResults<Track>> getUserTopTracks(
            @Header("Authorization") String authorization,
            @Query("time_range") String timeRange
    );

    @GET("users/{user_id}")
    Call<User> getUserProfile(
            @Header("Authorization") String authorization,
            @Path("user_id") String userId
    );

    @GET("artists/{id}")
    Call<Artist> getArtist(
            @Header("Authorization") String authorization,
            @Path("id") String id
    );

    @GET("artists")
    Call<Artists> getSeveralArtists(
            @Header("Authorization") String authorization,
            @Query(value = "ids", encoded = true) String ids
    );

    @GET("albums/{id}")
    Call<Album> getAlbum(
            @Header("Authorization") String authorization,
            @Path("id") String id
    );

    @GET("albums")
    Call<Albums> getSeveralAlbums(
            @Header("Authorization") String authorization,
            @Query(value = "ids", encoded = true) String ids
    );

    @GET("tracks/{id}")
    Call<Track> getTrack(
            @Header("Authorization") String authorization,
            @Path("id") String id
    );

    @GET("tracks")
    Call<Tracks> getSeveralTracks(
            @Header("Authorization") String authorization,
            @Query(value = "ids", encoded = true) String ids
    );

    @GET("recommendations")
    Call<Recommendations> getRecommendations(
            @Header("Authorization") String authorization,
            @Query(value = "seed_genres", encoded = true) String seedGenres,
            @Query(value = "seed_artists", encoded = true) String seedArtists);

    @POST("users/{user_id}/playlists")
    Call<Playlist> postPlaylist(
            @Header("Authorization") String authorization,
            @Path("user_id") String userId,
            @Body Map<String, Object> body
    );

    @POST("playlists/{playlist_id}/tracks")
    Call<Object> addTracksToPlaylist(
            @Header("Authorization") String authorization,
            @Path("playlist_id") String playlistId,
            @Body UrisRequestBody body
    );
}
