package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.*;
import com.example.Capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final BoatRepository boatRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;
    private final SendMailService sendMailService;
    private final EmergencyRepository emergencyRepository;


    public List<Trip> getTrips() {
        return tripRepository.findAll();
    }


    public void addTrip(Integer boatOwnerId, Integer boatId, Trip trip) {
        Boat boat = boatRepository.findBoatById(boatId);
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        if (boat == null || boatOwner == null) {
            throw new ApiException("Boat or boat owner not found");
        }
        if (boatOwner.getStatus().equalsIgnoreCase("SUSPENDED")) {
            throw new ApiException("This boat owner cannot add trip his account must be active");
        }
        if (!boatOwner.getBoats().contains(trip.getBoat())) {
            throw new ApiException("The boat owner cannot add this trip because he does not have the appropriate boat");
        }
        trip.setBoat(boat);
        trip.setBoatOwner(boatOwner);
        Long DurationHours = Duration.between(trip.getStartDate(), trip.getEndDate()).toHours();
        trip.setTotalPrice(DurationHours * trip.getBoat().getPricePerHour());
        tripRepository.save(trip);
    }

    public void assignDriverToTrip(Integer driverId, Integer tripId) {
        Driver driver = driverRepository.findDriverById(driverId);
        Trip trip = tripRepository.findTripById(tripId);
        if (driver == null || trip == null) {
            throw new ApiException("Driver or Trip not found");
        }

        trip.setDriver(driver);
        Long durationHours = Duration.between(trip.getStartDate(), trip.getEndDate()).toHours();
        trip.setTotalPrice(trip.getTotalPrice() + durationHours * driver.getHourlyWage());
        tripRepository.save(trip);
    }

    public void updateTrip(Integer id, Trip trip) {
        Trip oldTrip = tripRepository.findTripById(id);
        if (oldTrip == null) {
            throw new ApiException("Trip not found");
        }
        Boat boat = boatRepository.findBoatById(trip.getBoat().getId());
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(trip.getBoatOwner().getId());
        if (boat == null || boatOwner == null) {
            throw new ApiException("Boat or boat owner not found");
        }
        if (!boatOwner.getBoats().contains(trip.getBoat())) {
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

    public void deleteTrip(Integer id) {
        Trip trip = tripRepository.findTripById(id);
        if (trip == null) {
            throw new ApiException("Trip not found");
        }
        tripRepository.delete(trip);
    }

    //===============================================================================================

    public void customizedTrip(Integer customerId , Trip trip){
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        trip.setStatus("Request");
        trip.setIsRequested(true);
        trip.setCustomer(customer);
        tripRepository.save(trip);
        //Email

        BoatOwner boatOwner = trip.getBoatOwner();

        if (boatOwner == null) {
            throw new ApiException("Boat owner not found");
        }

            String subject = "New Trip Request";

            String body =
                    "Hello " + boatOwner.getFullName() + ",\n\n" +
                            "There is a new trip request:\n" +
                            trip.getTitle() + "\n\n" +
                            "from: "+customer.getName() +
                            "Trip Details:\n" +
                            "- Start: " + trip.getStartDate() + "\n" +
                            "- End: " + trip.getEndDate() +
                            "- From: " + trip.getStartLocation() + "\n" +
                            "- To: " + trip.getDestinationLocation() + "\n\n"
                            ;

            sendMailService.sendMessage(boatOwner.getEmail(), subject, body);
    }

    public void approveTripRequest(Integer boatOwnerId , Integer tripId){
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        Trip trip = tripRepository.findTripById(tripId);
        Customer customer = trip.getCustomer();
        if(boatOwner == null ){
            throw new ApiException("Boat owner not found");
        }
        if(!trip.getBoatOwner().equals(boatOwner)){
            throw new ApiException("Boat owner not unauthorized to approve this trip");
        }
        trip.setStatus("Upcoming");
        customer.getMyTrips().add(trip);
        tripRepository.save(trip);
        customerRepository.save(customer);

        String subject = "Customized Trip Accepted ✅";

        String body =
                "Hello " + customer.getName() + ",\n\n" +
                        "Your request for the customized trip:\n" +
                        trip.getTitle() + "\n\n" +
                        "Has been ACCEPTED successfully ✅\n\n" +
                        "Trip Details:\n" +
                        "- Start: " + trip.getStartDate() + "\n" +
                        "- From: " + trip.getStartLocation() + "\n" +
                        "- To: " + trip.getDestinationLocation() + "\n\n" +
                        "Enjoy your trip 🌊🚤";

        sendMailService.sendMessage(customer.getEmail(), subject, body);
    }



    public List<Trip> getTripsByStatus(String status){
       return tripRepository.findTripByStatus(status);
    }

    public List<Trip> getTripsByDestinationLocation(String destinationLocation){
        return tripRepository.findTripByDestinationLocation(destinationLocation);
    }

        //------------------------------------------------

    public Map<Trip, Timer> timers = new HashMap<>();
    public void startTrip(Integer tripId, Integer ownerId) {
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null || owner == null) throw new ApiException("owner or trip not found");
        if (owner != trip.getBoatOwner())
            throw new ApiException("Authorization failed: this trip belongs to another owner"); //review this line

        if (!trip.getStatus().equals("Upcoming")) throw new ApiException("trip is not in Upcoming state");

        trip.setStatus("Ongoing");
        tripRepository.save(trip);
//start timer to date
        Timer timer = new Timer();
        timers.put(trip, timer);

        //calculate timer seconds from endDate
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = trip.getEndDate();
        long delayMillis = Duration.between(now, end).toMillis();
        if (delayMillis <= 0) throw new ApiException("");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Times out");
                //call emergency

            }
        }, delayMillis * 1000);
    }


    public void endTrip(Integer tripId, Integer ownerId) {

        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null || owner == null) throw new ApiException("owner or trip not found");
        if (owner != trip.getBoatOwner())
            throw new ApiException("Authorization failed: this trip belongs to another owner"); //review this line

        if (!trip.getStatus().equals("Ongoing")) throw new ApiException("trip is not in Ongoing state");

        trip.setStatus("Completed");
        tripRepository.save(trip);

        //stop the timer
        if (timers.get(trip) != null) {
            Timer timer = timers.get(trip);
            timer.cancel();
            timer.purge();
            timers.remove(timer);
        }
    }


    public ArrayList<Trip> recommendationTrips(String prompt) {
        ArrayList<Trip> aiRecommendations = new ArrayList<>();
        //fix repo
        if (tripRepository.findTripByStatus("Upcoming").isEmpty()) return aiRecommendations;

        // 1> create DTO for available trips to AI
        List<Trip> availableTrips = tripRepository.findTripByStatus("Upcoming");


        // 2> send prompt
        prompt = "I want you to recommend me suitable cruise of these available trips, I want you to select the potential trip, so it could be one or more of potential trip that I am looking for " +

                "please answer me with trip Ids only separated by - , for example : 2-4-7-15 " +
                "if there is no trip that are potential i am looking for from given list, I want you to reply me with -1 " +
                "be direct and don't answer me anything but the final result for example 2-4-7-15 or -1 ,,, here is the list:" + " and here is description of my preferences : " + prompt;


        // 3> read result


        return aiRecommendations;
    }

    public void sendEmergency(Integer tripId) {
        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null) throw new ApiException("trip not found");

        Customer customer = trip.getCustomer();
       // Emergency emergency =

    }


}
