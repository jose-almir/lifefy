package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties("seeds")
public class Recommendations {
    private List<Track> tracks;

    public String getTracksUri() {
        return tracks.stream().map(Track::getUri).collect(Collectors.joining(","));
    }
}
