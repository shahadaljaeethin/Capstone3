package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Service.BoatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boat")
@RequiredArgsConstructor
public class BoatController {

    private final BoatService boatService;

    @GetMapping("/get")
    public ResponseEntity<List<Boat>> getAllBoats() {
        return ResponseEntity.status(200).body(boatService.getAllBoats());
    }

    @PostMapping("/add/{ownerId}/{categoryId}")
    public ResponseEntity<?> addBoat(@PathVariable Integer ownerId, @PathVariable Integer categoryId, @Valid @RequestBody Boat boat) {

        boatService.addBoat(ownerId, categoryId, boat);
        return ResponseEntity.status(200).body(new ApiResponse("Boat added successfully"));
    }

    @PutMapping("/update/{boatId}/{ownerId}/{categoryId}")
    public ResponseEntity<?> updateBoat(@PathVariable Integer boatId, @PathVariable Integer ownerId, @PathVariable Integer categoryId, @Valid @RequestBody Boat boat) {

        boatService.updateBoat(boatId, ownerId, categoryId, boat);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Boat updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoat(@PathVariable Integer id) {

        boatService.deleteBoat(id);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Boat deleted successfully"));
    }

    @PutMapping("/update-status/{boatId}/{status}")
    public ResponseEntity<?> updateBoatStatus(@PathVariable Integer boatId,
                                                        @PathVariable String status) {

        boatService.updateBoatStatus(boatId, status);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Boat status updated successfully"));
    }
}
