package com.rasha.travel_assistant.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WeatherClient {

    private final WebClient webClient;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.weatherapi.com/v1")
                .build();
    }

    @Retry(name = "weatherApi")
    @CircuitBreaker(name = "weatherApi", fallbackMethod = "weatherFallback")
    public String getWeather(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", apiKey)
                        .queryParam("q", city)
                        .queryParam("aqi", "no")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String weatherFallback(String city, Throwable throwable) {
        return "Weather service is currently unavailable for " + city;
    }
}