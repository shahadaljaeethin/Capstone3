package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripSafetyGuideDTO {

    private String title;
    private List<String> beforeTrip;
    private List<String> duringTrip;
    private List<String> emergencyActions;
}
