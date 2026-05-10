package com.rasha.travel_assistant.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rasha.travel_assistant.dto.WeatherResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    public Mono<String> getWeather(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", apiKey)
                        .queryParam("q", city)
                        .queryParam("aqi", "no")
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .map(response -> {
                    if (response == null || response.getCurrent() == null) {
                        return "Weather information is unavailable.";
                    }

                    return response.getCurrent().getTemp_c()
                            + "°C, "
                            + response.getCurrent().getCondition().getText();
                })
                .doOnError(error -> System.out.println(error.getMessage()))
                .onErrorResume(error ->
                        Mono.just(
                                "Weather service error: " + error.getMessage()
                        )
                );
    }

    public Mono<String> weatherFallback(String city, Throwable throwable) {
        return Mono.just("Weather service is currently unavailable for " + city);
    }
}