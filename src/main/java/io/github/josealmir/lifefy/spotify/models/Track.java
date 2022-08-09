package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Track {
    private Album album;
    private List<Artist> artists;
    private List<String> availableMarkets;
    private long discNumber;
    private long durationMS;
    private boolean explicit;
    private ExternalIds externalIDS;
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private boolean isPlayable;
    private LinkedFrom linkedFrom;
    private Restrictions restrictions;
    private String name;
    private long popularity;
    private String previewURL;
    private long trackNumber;
    private String type;
    private String uri;
    private Boolean isLocal;

    public String getAllArtists() {
        return artists.stream().map(Artist::getName).collect(Collectors.joining(", "));
    }

    public String getDuration() {
        long hours = (durationMS / (60 * 60 * 1000));
        long minutes = (durationMS / 1000) / 60;
        long seconds = (durationMS / 1000) % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        return String.format("%02d:%02d", minutes, seconds);
    }
}
