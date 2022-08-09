package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Album {
    private String albumType;
    private long totalTracks;
    private List<String> availableMarkets;
    private ExternalUrls externalUrls;
    private ExternalIds externalIds;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private String label;
    private String releaseDate;
    private String releaseDatePrecision;
    private Restrictions restrictions;
    private String type;
    private String uri;
    private List<String> genres;
    private List<Artist> artists;
    private ItemTracks tracks;
    private List<Copyright> copyrights;
    private long popularity;
    private Boolean isLocal;
}
