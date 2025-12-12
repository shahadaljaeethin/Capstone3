package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoatRecommendationDTO {
    private Integer boatId;
    private Double score;
    private String reason;
}
