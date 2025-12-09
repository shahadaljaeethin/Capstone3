package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Driver;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import com.example.Capstone3.Repository.BoatRepository;
import com.example.Capstone3.Repository.DriverRepository;
import com.example.Capstone3.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final BoatRepository boatRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final DriverRepository driverRepository;


    public List<Trip> getTrips(){
        return tripRepository.findAll();
    }

    // هل اخلي ال boat owner يدخل ال id حقه هنا ك path variable
    public void addTrip(Trip trip){
        Boat boat = boatRepository.findBoatById(trip.getBoat().getId());
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(trip.getBoatOwner().getId());
        if(boat == null || boatOwner == null){
            throw  new ApiException("Boat or boat owner not found");
        }
        if (boatOwner.getStatus().equalsIgnoreCase("SUSPENDED")){
            throw new ApiException("This boat owner cannot add trip his account must be active");
        }
        if(!boatOwner.getBoats().contains(trip.getBoat())){
            throw new ApiException("The boat owner cannot add this trip because he does not have the appropriate boat");
        }
        //باقي حساب total price
        tripRepository.save(trip);
    }

    public void assignBoatToTrip(Integer boatId, Integer tripId){
        Boat boat = boatRepository.findBoatById(boatId);
        Trip trip = tripRepository.findTripById(tripId);
        if(boat == null || trip == null){
            throw new ApiException("Boat or Trip not found");
        }
        trip.setBoat(boat);
        tripRepository.save(trip);
    }

    public void assignDriverToTrip(Integer driverId, Integer tripId){
        Driver driver = driverRepository.findDriverById(driverId);
        Trip trip = tripRepository.findTripById(tripId);
        if(driver == null || trip == null){
            throw new ApiException("Driver or Trip not found");
        }
        trip.setDriver(driver);
        tripRepository.save(trip);
    }

    public void updateTrip(Integer id, Trip trip){
        Trip oldTrip = tripRepository.findTripById(id);
        if(oldTrip == null){
            throw new ApiException("Trip not found");
        }
        Boat boat = boatRepository.findBoatById(trip.getBoat().getId());
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(trip.getBoatOwner().getId());
        if(boat == null || boatOwner == null){
            throw  new ApiException("Boat or boat owner not found");
        }
        if(!boatOwner.getBoats().contains(trip.getBoat())){
            throw new ApiException("The boat owner cannot add this trip because he does not have the appropriate boat");
        }

        oldTrip.setTitle(trip.getTitle());
        oldTrip.setTripType(trip.getTripType());
        oldTrip.setFishingGear(trip.isFishingGear());
        oldTrip.setStartDate(trip.getStartDate());
        oldTrip.setEndDate(trip.getEndDate());
        oldTrip.setStartLocation(trip.getStartLocation());
        oldTrip.setEndLocation(trip.getEndLocation());
        oldTrip.setDestinationLocation(trip.getDestinationLocation());
        oldTrip.setStatus(trip.getStatus());
        oldTrip.setTotalPrice(trip.getTotalPrice());
        oldTrip.setDriver(trip.getDriver());
        oldTrip.setBoat(trip.getBoat());
        oldTrip.setBoatOwner(trip.getBoatOwner());
        tripRepository.save(oldTrip);

    }

    public void deleteTrip(Integer id){
        Trip trip = tripRepository.findTripById(id);
        if(trip == null){
            throw new ApiException("Trip not found");
        }
        tripRepository.delete(trip);
    }



}
