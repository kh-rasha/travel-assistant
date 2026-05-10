package com.rasha.travel_assistant.controller;

import com.rasha.travel_assistant.dto.TravelResponse;
import com.rasha.travel_assistant.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/api/travel")
    public TravelResponse getTravelAdvice(@RequestParam String city) {
        return travelService.getTravelAdvice(city);
    }
}