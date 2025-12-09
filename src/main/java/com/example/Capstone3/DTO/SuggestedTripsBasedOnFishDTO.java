package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestedTripsBasedOnFishDTO {

    private String fishType;
    private List<Integer> recommendedTripIds;
    private List<String> reasons;

}
