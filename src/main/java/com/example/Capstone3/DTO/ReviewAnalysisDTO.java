package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAnalysisDTO {
    private Integer totalReviews;
    private Double averageRating;
    private List<String> commonComplaints;
    private List<String> commonPraises;
    private List<String> improvementSuggestions;
    private List<String> strengths;
}
