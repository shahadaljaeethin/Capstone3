package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Model.Review;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import com.example.Capstone3.Repository.BoatRepository;
import com.example.Capstone3.Repository.CustomerRepository;
import com.example.Capstone3.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final CustomerRepository customerRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Integer ownerId, Integer customerId, Review review) {
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("owner not found");
        }

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        Review oldReview = reviewRepository.findReviewByBoatIdAndCustomerId(ownerId, customerId);

        if (oldReview != null) {
            throw new ApiException("Customer already reviewed this boat");
        }

            review.setBoatOwner(owner);
        review.setCustomer(customer);

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
}
