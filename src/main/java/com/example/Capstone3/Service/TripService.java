package com.example.Capstone3.Service;

import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public List<Trip> getTrips(){
        return tripRepository.findAll();
    }

    public void addTrip(Trip trip){

    }
}
