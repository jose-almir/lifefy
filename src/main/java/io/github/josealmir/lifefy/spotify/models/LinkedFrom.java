package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LinkedFrom {
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
    private boolean isLocal;
}
