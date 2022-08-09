package io.github.josealmir.lifefy.config;

import io.github.josealmir.lifefy.spotify.SpotifyApiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Value("${spotify.base-url}")
    private String spotifyUrl;

    @Bean
    public SpotifyApiRepository spotifyApiRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(spotifyUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(SpotifyApiRepository.class);
    }
}
