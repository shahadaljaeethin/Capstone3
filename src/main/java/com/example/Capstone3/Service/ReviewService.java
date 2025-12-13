package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Model.Review;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import com.example.Capstone3.Repository.CustomerRepository;
import com.example.Capstone3.Repository.ReviewRepository;
import com.example.Capstone3.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Integer ownerId, Integer customerId, Review review) {

        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        if(tripRepository.findTripByBoatOwner_IdAndCustomer_IdAndStatus(ownerId,customerId,"Completed").isEmpty()) throw new ApiException("you haven't completed any trip with this owner");
        review.setBoatOwner(owner);
        review.setCustomer(customer);
        review.setCreatAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void updateReview(Integer reviewId, Review review) {

        Review oldReview = reviewRepository.findById(reviewId).orElse(null);

        if (oldReview == null) {
            throw new ApiException("Review not found");
        }

        oldReview.setRating(review.getRating());
        oldReview.setComment(review.getComment());

        reviewRepository.save(oldReview);
    }

    public void deleteReview(Integer reviewId) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            throw new ApiException("Review not found");
        }

        reviewRepository.delete(review);
    }

    public BoatOwner getTopRatedBoatOwner() {
        return reviewRepository.findTopRatedBoatOwner();
    }
}
