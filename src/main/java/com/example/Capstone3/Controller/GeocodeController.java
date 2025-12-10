package com.example.Capstone3.Controller;

import com.example.Capstone3.Service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/geocode")
@RestController
@RequiredArgsConstructor
public class GeocodeController {

    private final GeocodingService geocodingService;

    @GetMapping("/get-coordinate/{place}")
    public ResponseEntity<?> getCoordinates(@PathVariable String place) {
        return ResponseEntity.status(200).body(geocodingService.getLocationCoordinates(place));
    }


}
