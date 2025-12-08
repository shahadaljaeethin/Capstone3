package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Emergency;
import com.example.Capstone3.Service.EmergencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emergency")
@RequiredArgsConstructor
public class EmergencyController {
    private final EmergencyService emergencyService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(emergencyService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmergency(@RequestBody @Valid Emergency emergency) {
        emergencyService.addEmergency(emergency);
        return ResponseEntity.status(200).body(new ApiResponse("Emergency contact added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmergency(@PathVariable Integer id, @RequestBody @Valid Emergency emergency) {
        emergencyService.updateEmergency(id, emergency);
        return ResponseEntity.status(200).body(new ApiResponse("Emergency contact updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmergency(@PathVariable Integer id) {
        emergencyService.deleteEmergency(id);
        return ResponseEntity.status(200).body(new ApiResponse("Emergency contact deleted"));
    }

}
