package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.DriverRequest;
import com.example.Capstone3.Service.DriverRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver-request")
@RequiredArgsConstructor
public class DriverRequestController {

    private final DriverRequestService driverRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getDriverRequests(){
        return ResponseEntity.status(200).body(driverRequestService.getDriverRequests());
    }

    @PostMapping("/add/{boatOwnerId}/{driverId}/{tripId}")
    public ResponseEntity<?> addDriverRequest(@PathVariable Integer boatOwnerId,@PathVariable Integer driverId,@PathVariable Integer tripId ,@RequestBody @Valid DriverRequest driverRequest){
        driverRequestService.addDriverRequest(boatOwnerId,driverId,tripId,driverRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Driver request added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDriverRequest(@PathVariable Integer id , @RequestBody DriverRequest driverRequest){
        driverRequestService.updateDriverRequest(id, driverRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Driver request updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDriverRequest(@PathVariable Integer id){
        driverRequestService.deleteDriverRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("Driver request deleted successfully"));
    }
    //==============
    @PutMapping("/accept/{driver}/{request}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer driver,@PathVariable Integer request){
    driverRequestService.acceptRequest(driver,request);
        return ResponseEntity.status(200).body(new ApiResponse("Request accepted"));
    }

    @PutMapping("/apology/{driver}/{request}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer driver,@PathVariable Integer request){
        driverRequestService.apologizeRequest(driver,request);
        return ResponseEntity.status(200).body(new ApiResponse("Request apology"));
    }
}
