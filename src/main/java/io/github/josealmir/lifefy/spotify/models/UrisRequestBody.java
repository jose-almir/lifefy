package io.github.josealmir.lifefy.spotify.models;

import lombok.Data;

import java.util.List;

@Data
public class UrisRequestBody {
    private List<String> uris;
    private Integer position;
}
