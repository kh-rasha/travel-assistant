package com.rasha.travel_assistant.service;

import com.rasha.travel_assistant.dto.RecommendationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TravelService {

    public Mono<RecommendationResponse> getRecommendations(String location) {
        return Mono.just(new RecommendationResponse(
                location,
                "Weather will be loaded from WeatherAPI",
                "museum",
                List.of(
                        "Visit a local museum",
                        "Explore the city center",
                        "Try a nearby cafe"
                )
        ));
    }
}