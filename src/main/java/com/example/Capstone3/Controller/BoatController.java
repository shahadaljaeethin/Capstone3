package com.example.Capstone3.Controller;

import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Service.BoatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boat")
@RequiredArgsConstructor
public class BoatController {

    private final BoatService boatService;

    @GetMapping("/get")
    public ResponseEntity<?> getBoats(){
        return ResponseEntity.status(200).body(boatService.getBoats());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBoat(@RequestBody Boat boat){
        boatService.addBoat(boat);
        return ResponseEntity.status(200).body("Boat added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBoat(@PathVariable Integer id, @RequestBody Boat boat){
        boatService.updateBoat(id, boat);
        return ResponseEntity.status(200).body("Boat updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoat(@PathVariable Integer id){
        boatService.deleteBoat(id);
        return ResponseEntity.status(200).body("Boat deleted successfully");
    }
}
