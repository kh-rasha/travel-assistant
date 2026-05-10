package com.rasha.travel_assistant.service;

import com.rasha.travel_assistant.client.OpenAiClient;
import com.rasha.travel_assistant.client.WeatherClient;
import com.rasha.travel_assistant.dto.TravelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final WeatherClient weatherClient;
    private final OpenAiClient openAiClient;

    public TravelResponse getTravelAdvice(String city) {
        String weatherSummary = weatherClient.getWeather(city);
        String travelAdvice = openAiClient.getTravelAdvice(city, weatherSummary);

        return new TravelResponse(city, weatherSummary, travelAdvice);
    }
}