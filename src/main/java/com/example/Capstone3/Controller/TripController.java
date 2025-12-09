package com.example.Capstone3.Controller;
import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trip")
@RequiredArgsConstructor

public class TripController {

    private final TripService tripService;

    @GetMapping("/get")
    public ResponseEntity<?> getTrips(){
        return ResponseEntity.status(200).body(tripService.getTrips());
    }

    @PostMapping("/add/{boatOwnerId}/{boatId}")
    public ResponseEntity<?> addTrip(@PathVariable Integer boatOwnerId,@PathVariable Integer boatId,@RequestBody @Valid Trip trip){
        tripService.addTrip(boatOwnerId,boatId,trip);
        return ResponseEntity.status(200).body(new ApiResponse("Trip added successfully"));
    }

    @PutMapping("/assign-driver-trip/{driverId}/{tripId}")
    public ResponseEntity<?> assignDriverToTrip(@PathVariable Integer driverId, @PathVariable Integer tripId){
        tripService.assignDriverToTrip(driverId, tripId);
        return ResponseEntity.status(200).body(new ApiResponse("Driver assigned successfully to trip "));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Integer id,@RequestBody @Valid Trip trip){
        tripService.updateTrip(id, trip);
        return ResponseEntity.status(200).body(new ApiResponse("Trip updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Integer id){
        tripService.deleteTrip(id);
        return ResponseEntity.status(200).body(new ApiResponse("Trip deleted successfully"));
    }
}
