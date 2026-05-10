package com.rasha.travel_assistant.controller;

import com.rasha.travel_assistant.dto.TravelResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TravelController {

    @GetMapping("/api/travel")
    public TravelResponse getTravelAdvice(@RequestParam String city) {
        return new TravelResponse(
                city,
                "Weather info will come from Weather API later",
                "AI travel advice will come from OpenAI later"
        );
    }
}