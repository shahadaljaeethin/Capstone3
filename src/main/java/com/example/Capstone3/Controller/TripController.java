package com.example.Capstone3.Controller;
import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Service.TripService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @PostMapping("/request-customized-trip/{customerId}")
    public  ResponseEntity<?> customizedTrip(@PathVariable Integer customerId , @RequestBody @Valid Trip trip){
        tripService.requestCustomizedTrip(customerId, trip);
        return ResponseEntity.status(200).body(new ApiResponse("Request customized trip send to boat owner successfully"));
    }

    @PutMapping("/approve-customized-trip/{boatOwnerId}/{tripId}")
    public ResponseEntity<?> approveCustomizedTripRequest(@PathVariable Integer boatOwnerId , @PathVariable Integer tripId){
        tripService.approveCustomizedTrip(boatOwnerId, tripId);
        return ResponseEntity.status(200).body(new ApiResponse("Customized trip approved successfully"));
    }

    @PutMapping("/reject-customized-trip/{boatOwnerId}/{tripId}")
    public ResponseEntity<?> rejectCustomizedTripRequest(@PathVariable Integer boatOwnerId , @PathVariable Integer tripId){
        tripService.rejectCustomizedTrip(boatOwnerId, tripId);
        return ResponseEntity.status(200).body(new ApiResponse("Customized trip rejected successfully"));
    }

    @GetMapping("/get-trips-by-status/{status}")
    public ResponseEntity<?> getTripsByStatus(@PathVariable String status){
        return ResponseEntity.status(200).body(tripService.getTripsByStatus(status));
    }

    @GetMapping("/get-trips-by-destination-location/{destination}")
    public ResponseEntity<?> getTripsByDestinationLocation(@PathVariable String destination){
        return ResponseEntity.status(200).body(tripService.getTripsByDestinationLocation(destination));
    }


    //Is post mapping ? or get , for real
    @PostMapping("/send emergency/{tripId}")
    public ResponseEntity<?> sendEmergency(@PathVariable Integer tripId){
    tripService.sendEmergency(tripId,true);
        return ResponseEntity.status(200).body(new ApiResponse("Emergency request send to Owner and Emergency contact successfully"));
    }

    @PutMapping("/start trip/{ownerId}/{tripId}")
    public ResponseEntity<?> startTrip(@PathVariable Integer tripId,@PathVariable Integer ownerId){
    tripService.startTrip(tripId,ownerId);
     return ResponseEntity.status(200).body(new ApiResponse("Trip started"));
    }
    @PutMapping("/end trip/{ownerId}/{tripId}")
    public ResponseEntity<?> endTrip(@PathVariable Integer tripId,@PathVariable Integer ownerId){
        tripService.endTrip(tripId,ownerId);
        return ResponseEntity.status(200).body(new ApiResponse("Trip completed"));
    }
    //**************************************************************************************** NOT COMPLETED SERVICE
    @GetMapping("/recommend trip/{query}")
    public ResponseEntity<?> recommendMeTrip(@PathVariable String query){
        return ResponseEntity.status(200).body(tripService.recommendationTrips(query));
    }
    //****************************************************************************************

    @PutMapping("/set discount/owner{ownerId}/trip{tripId}/{discountPrec}/date{date}")
    public ResponseEntity<?> discountTripTilLDate(@PathVariable Integer tripId, @PathVariable Integer ownerId, @PathVariable Double discountPrec, @PathVariable LocalDate date){
    tripService.discountTripTillDate(tripId,ownerId,discountPrec,date);
    return ResponseEntity.status(200).body(new ApiResponse("Trip set on discount till "+date));
    }

    @GetMapping("/pre ask/trip{tripId}/{start}/{end}")
    public ResponseEntity<?> tripPreAskDate(@PathVariable Integer tripId,@PathVariable LocalDateTime start,@PathVariable LocalDateTime end){
        return ResponseEntity.status(200).body(tripService.tripPreAskDate(tripId,start,end));
    }
    //*********Put?
    @PostMapping("/ask changing date/customer{customerId}/trip{tripId}/{start}/{end}")
    public ResponseEntity<?> tripAskDate(@PathVariable Integer tripId,@PathVariable Integer customerId,@PathVariable LocalDateTime start,@PathVariable LocalDateTime end) {
    tripService.tripAskDate(tripId,customerId,start,end);
    return ResponseEntity.status(200).body(new ApiResponse("Request send to trip owner"));
    }

    @GetMapping("/get trip by owner/{owner}")
    public ResponseEntity<?> getTripByOwner(@PathVariable Integer owner){
        return ResponseEntity.status(200).body(tripService.getTribByOwner(owner));
    }
}
