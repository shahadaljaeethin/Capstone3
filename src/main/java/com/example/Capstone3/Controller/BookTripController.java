package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Service.BookTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book-trip")
@RequiredArgsConstructor
public class BookTripController {

    private final BookTripService bookTripService;

    @GetMapping("/get")
    public ResponseEntity<?> getBookTrips() {
        return ResponseEntity.status(200).body(bookTripService.getBookTrip());
    }

    @PostMapping("/add/{tripId}/{customerId}")
    public ResponseEntity<?> addBookTrip(@PathVariable Integer tripId,
                                         @PathVariable Integer customerId) {

        bookTripService.addBookTrip(tripId, customerId);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Book trip added successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookTrip(@PathVariable Integer id) {

        bookTripService.deleteBookTrip(id);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Book trip deleted successfully"));
    }

    @PutMapping("/accept/{ownerId}/{bookTripId}")
    public ResponseEntity<?> acceptBooking(@PathVariable Integer ownerId,
                                           @PathVariable Integer bookTripId) {

        bookTripService.acceptBooking(ownerId, bookTripId);
        return ResponseEntity.status(200).body("Booking accepted successfully");
    }

    @PutMapping("/reject/{ownerId}/{bookTripId}")
    public ResponseEntity<?> rejectBooking(@PathVariable Integer ownerId,
                                           @PathVariable Integer bookTripId) {

        bookTripService.rejectBooking(ownerId, bookTripId);
        return ResponseEntity.status(200).body("Booking rejected successfully");
    }
}
