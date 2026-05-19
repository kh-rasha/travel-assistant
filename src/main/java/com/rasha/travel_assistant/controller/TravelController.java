package com.rasha.travel_assistant.controller;

import com.rasha.travel_assistant.dto.RecommendationResponse;
import com.rasha.travel_assistant.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/api/recommendations")
    public Mono<RecommendationResponse> getRecommendations(@RequestParam String location) {
        return travelService.getRecommendations(location);
    }
}