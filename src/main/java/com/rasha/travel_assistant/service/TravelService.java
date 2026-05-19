package com.rasha.travel_assistant.service;

import com.rasha.travel_assistant.client.WeatherClient;
import com.rasha.travel_assistant.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final WeatherClient weatherClient;

    public Mono<RecommendationResponse> getRecommendations(String location) {
        return weatherClient.getWeather(location)
                .map(weatherSummary -> {
                    String recommendationType = chooseRecommendationType(weatherSummary);
                    List<String> activities = getFallbackActivities(recommendationType);

                    return new RecommendationResponse(
                            location,
                            weatherSummary,
                            recommendationType,
                            activities
                    );
                });
    }

    private String chooseRecommendationType(String weatherSummary) {
        String weather = weatherSummary.toLowerCase();

        if (weather.contains("rain") || weather.contains("snow") || weather.contains("storm")) {
            return "museum";
        }

        if (weather.contains("sunny") || weather.contains("clear")) {
            return "park";
        }

        if (weather.contains("cloud") || weather.contains("overcast")) {
            return "cafe";
        }

        return "tourist attraction";
    }

    private List<String> getFallbackActivities(String recommendationType) {
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