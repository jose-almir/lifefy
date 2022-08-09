package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Playlist {
    private boolean collaborative;
    private String description;
    private ExternalUrls externalUrls;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private User owner;
    private String primaryColor;
    private Boolean playlistPublic;
    @JsonProperty("public")
    private Boolean _public;
    private String snapshotID;
    private ItemTracks tracks;
    private String type;
    private String uri;
}
