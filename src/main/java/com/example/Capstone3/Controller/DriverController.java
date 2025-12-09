package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Driver;
import com.example.Capstone3.Service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/add")
    public ResponseEntity<?> addDriver(@RequestBody @Valid Driver driver) {
        driverService.addDriver(driver);
        return ResponseEntity.status(200).body(new ApiResponse("Driver added"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllDrivers() {return ResponseEntity.status(200).body(driverService.getAll());
                                              }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Integer id, @RequestBody @Valid Driver driver) {
        driverService.updateDriver(id, driver);
        return ResponseEntity.status(200).body(new ApiResponse("Driver updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Integer id) {
        driverService.deleteDriver(id);
        return ResponseEntity.status(200).body(new ApiResponse("Driver removed"));
    }

    @PutMapping("/activate-account/{adminId}/{driverId}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer adminId , @PathVariable Integer driverId){
        driverService.activateAccount(adminId, driverId);
        return ResponseEntity.status(200).body(new ApiResponse("Driver account activated successfully"));
    }

}
