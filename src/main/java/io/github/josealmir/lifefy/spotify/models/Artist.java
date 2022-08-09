package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Artist {
    private ExternalUrls externalUrls;
    private Followers followers;
    private String[] genres;
    private String href;
    private String id;
    private Image[] images;
    private String name;
    private long popularity;
    private String type;
    private String uri;
}
