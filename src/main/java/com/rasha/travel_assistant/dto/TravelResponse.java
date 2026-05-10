package com.rasha.travel_assistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelResponse {
    private String city;
    private String weatherSummary;
    private String travelAdvice;
}