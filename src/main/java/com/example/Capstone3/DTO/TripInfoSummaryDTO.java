package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripInfoSummaryDTO {

    private Integer tripId;
    private String regionSummary;
    private String famousFor;
    private String popularFishTypes;
    private String tripExperience;

}
