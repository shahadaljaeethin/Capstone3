package com.example.Capstone3.Controller;

import com.example.Capstone3.DTO.TripRouteAdviceDTO;
import com.example.Capstone3.DTO.TripSafetyGuideDTO;
import com.example.Capstone3.Service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/trip-route/{tripId}")
    public ResponseEntity<?> getTripRouteAdvice(@PathVariable Integer tripId) {
        TripRouteAdviceDTO advice = aiService.getTripRouteAdvice(tripId);
        return ResponseEntity.status(200).body(advice);
    }

    @GetMapping("/trip-safety-guide")
    public ResponseEntity<?> getGeneralTripSafetyGuide() {
        TripSafetyGuideDTO guide = aiService.getGeneralTripSafetyGuide();
        return ResponseEntity.status(200).body(guide);
    }

//--------------------------------------------------------------

    @GetMapping("/trip-summary/{tripId}")
    public ResponseEntity<?> getTripSummary(@PathVariable Integer tripId) {
        return ResponseEntity.status(200).body(aiService.getTripInfoSummary(tripId));
    }

    @GetMapping("/suggest-trips-by-fish/{fishType}")
    public ResponseEntity<?> suggestTripsByFish(@PathVariable String fishType) {
        return ResponseEntity.status(200).body(aiService.getTripsByFishType(fishType));
    }



}
