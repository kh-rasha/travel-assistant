package com.rasha.travel_assistant.client;

import com.rasha.travel_assistant.dto.ActivityResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ActivitiesClient {

    private final WebClient webClient;

    @Value("${geoapify.api.key}")
    private String apiKey;

    public ActivitiesClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.geoapify.com/v2")
                .build();
    }

    @Retry(name = "activitiesApi")
    @CircuitBreaker(name = "activitiesApi", fallbackMethod = "activitiesFallback")
    public Mono<List<String>> getActivities(String location, String recommendationType) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/places")
                        .queryParam("categories", mapToGeoapifyCategory(recommendationType))
                        .queryParam("filter", "place:" + location)
                        .queryParam("limit", 5)
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ActivityResponse.class)
                .map(response -> {
                    if (response == null || response.getFeatures() == null || response.getFeatures().isEmpty()) {
                        return getDefaultActivities(recommendationType);
                    }

                    return response.getFeatures().stream()
                            .map(feature -> {
                                String name = feature.getProperties().getName();

                                if (name == null || name.isBlank()) {
                                    return "Recommended place in " + location;
                                }

                                return name;
                            })
                            .limit(5)
                            .toList();
                })
                .onErrorResume(error -> Mono.just(getDefaultActivities(recommendationType)));
    }

    public Mono<List<String>> activitiesFallback(
            String location,
            String recommendationType,
            Throwable throwable
    ) {
        return Mono.just(getDefaultActivities(recommendationType));
    }

    private String mapToGeoapifyCategory(String recommendationType) {
        return switch (recommendationType) {
            case "museum" -> "entertainment.museum";
            case "park" -> "leisure.park";
            case "cafe" -> "catering.cafe";
            default -> "tourism.sights";
        };
    }

    private List<String> getDefaultActivities(String recommendationType) {
        return switch (recommendationType) {
            case "museum" -> List.of(
                    "Visit a local museum",
                    "Explore an indoor art gallery",
                    "Try a cozy cafe nearby"
            );
            case "park" -> List.of(
                    "Walk in a city park",
                    "Visit an outdoor viewpoint",
                    "Explore the city center"
            );
            case "cafe" -> List.of(
                    "Try a popular local cafe",
                    "Visit a shopping street",
                    "Explore nearby indoor attractions"
            );
            default -> List.of(
                    "Explore the city center",
                    "Visit a famous landmark",
                    "Try local food"
            );
        };
    }
}