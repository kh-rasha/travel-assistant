package com.rasha.travel_assistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private String location;
    private String weatherSummary;
    private String recommendationType;
    private List<String> activities;
}