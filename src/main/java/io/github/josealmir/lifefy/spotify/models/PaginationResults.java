package io.github.josealmir.lifefy.spotify.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaginationResults<T> {
    private List<T> items;
    private long total;
    private long limit;
    private long offset;
    private String href;
    private String previous;
    private String next;
}
