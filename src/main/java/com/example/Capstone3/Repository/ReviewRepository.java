package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);

    @Query("SELECT r.boatOwner FROM Review r " +
            "GROUP BY r.boatOwner " +
            "ORDER BY AVG(r.rating) DESC")
    BoatOwner findTopRatedBoatOwner();

}
