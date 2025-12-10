package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);

}
