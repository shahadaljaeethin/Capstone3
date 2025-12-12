package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Service.BoatOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class BoatOwnerController {

    private final BoatOwnerService boatOwnerService;

    @GetMapping("/get")
    public ResponseEntity<?> getOwners(){
        return ResponseEntity.status(200).body(boatOwnerService.getOwners());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOwner(@RequestBody BoatOwner boatOwner){
        boatOwnerService.addOwner(boatOwner);
        return ResponseEntity.status(200).body(new ApiResponse("Owner added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Integer id, @RequestBody BoatOwner boatOwner){
        boatOwnerService.updateOwner(id, boatOwner);
        return ResponseEntity.status(200).body(new ApiResponse("Owner updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Integer id){
        boatOwnerService.deleteOwner(id);
        return ResponseEntity.status(200).body(new ApiResponse("Owner deleted successfully"));
    }

    @PutMapping("/activate-account/{adminId}/{boatOwnerId}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer adminId , @PathVariable Integer boatOwnerId){
        boatOwnerService.activateAccount(adminId, boatOwnerId);
        return ResponseEntity.status(200).body(new ApiResponse("Boat owner account activated successfully"));
    }
}
