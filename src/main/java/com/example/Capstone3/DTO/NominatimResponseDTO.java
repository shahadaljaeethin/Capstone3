package com.example.Capstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NominatimResponseDTO {

    private String lon;
    private String lat;
}
