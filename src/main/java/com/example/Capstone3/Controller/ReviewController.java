package com.example.Capstone3.Controller;

import com.example.Capstone3.Model.Review;
import com.example.Capstone3.Service.ReviewService;
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

    @PostMapping("/add/{boatId}/{customerId}")
    public ResponseEntity<?> addReview(@PathVariable Integer boatId, @PathVariable Integer customerId, @RequestBody Review review) {
        reviewService.addReview(boatId, customerId, review);
        return ResponseEntity.status(200).body("Review added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body("Review updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body("Review deleted successfully");
    }
}
