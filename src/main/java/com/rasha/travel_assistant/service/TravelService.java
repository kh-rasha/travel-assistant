package com.rasha.travel_assistant.service;

import com.rasha.travel_assistant.client.OpenAiClient;
import com.rasha.travel_assistant.client.WeatherClient;
import com.rasha.travel_assistant.dto.TravelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final WeatherClient weatherClient;
    private final OpenAiClient openAiClient;
    public Mono<TravelResponse> getTravelAdvice(String city) {
        return weatherClient.getWeather(city)
                .flatMap(weatherSummary ->
                        openAiClient.getTravelAdvice(city, weatherSummary)
                                .map(travelAdvice ->
                                        new TravelResponse(city, weatherSummary, travelAdvice)
                                )
                );
    }
}