package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    private String country;
    private String displayName;
    private String email;
    private ExplicitContent explicitContent;
    private ExternalUrls externalUrls;
    private Followers followers;
    private String href;
    private String id;
    private Image[] images;
    private String product;
    private String type;
    private String uri;
}
