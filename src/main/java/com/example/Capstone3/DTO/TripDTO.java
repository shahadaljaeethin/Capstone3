package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {

    private Integer id;
    private String title;
    private String description;
    private String tripType;
    private Boolean fishingGear;
    private String startDate;
    private String endDate;
    private String startLocation;
    private String destinationLocation;
    private String endLocation;

}
