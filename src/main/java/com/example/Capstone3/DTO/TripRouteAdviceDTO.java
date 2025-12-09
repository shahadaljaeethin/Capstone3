package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRouteAdviceDTO {

    private Integer tripId;
    private String shortSummary;
    private List<String> routeSuggestions;
    private List<String> timeSuggestions;
    private List<String> safetyTips;
    private List<String> extraIdeas;
}
