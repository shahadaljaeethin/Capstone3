package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Review;
import com.example.Capstone3.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @PostMapping("/add/{ownerId}/{customerId}")
    public ResponseEntity<?> addReview(@PathVariable Integer ownerId,
                                       @PathVariable Integer customerId,
                                       @RequestBody @Valid Review review) {
        reviewService.addReview(ownerId, customerId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id,
                                          @RequestBody @Valid Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }

    @GetMapping("/get-top-rated-boat-owner")
    public ResponseEntity<?> getTopRatedBoatOwner() {
        return ResponseEntity.status(200).body(reviewService.getTopRatedBoatOwner());
    }
}
