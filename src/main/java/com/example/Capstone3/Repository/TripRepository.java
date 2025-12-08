package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    Trip findTripById(Integer id);
}
