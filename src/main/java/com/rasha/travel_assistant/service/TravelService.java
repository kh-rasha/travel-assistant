package com.rasha.travel_assistant.service;

import com.rasha.travel_assistant.client.ActivitiesClient;
import com.rasha.travel_assistant.client.WeatherClient;
import com.rasha.travel_assistant.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final WeatherClient weatherClient;
    private final ActivitiesClient activitiesClient;

    public Mono<RecommendationResponse> getRecommendations(String location) {
        return weatherClient.getWeather(location)
                .flatMap(weatherSummary -> {
                    String recommendationType = chooseRecommendationType(weatherSummary);

                    return activitiesClient.getActivities(location, recommendationType)
                            .map(activities -> new RecommendationResponse(
                                    location,
                                    weatherSummary,
                                    recommendationType,
                                    activities
                            ));
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
}