package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.*;
import com.example.Capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
    private final GeocodingService geocodingService;

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

        if(trip.getEndDate().isBefore(trip.getStartDate())) throw new ApiException("*end date must be after start date");
        Long DurationHours = Duration.between(trip.getStartDate(), trip.getEndDate()).toHours();
        if(DurationHours < 2) throw new ApiException("minimum duration for a trip is two hours");

        trip.setBoat(boat);
        trip.setBoatOwner(boatOwner);

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

    public void requestCustomizedTrip(Integer customerId , Trip trip){
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

    public void approveCustomizedTrip(Integer boatOwnerId , Integer tripId){

        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        Trip trip = tripRepository.findTripById(tripId);
        Customer customer = trip.getCustomer();
        if(boatOwner == null ){
            throw new ApiException("Boat owner not found");
        }
        if(!trip.getBoatOwner().equals(boatOwner)){
            throw new ApiException("Boat owner unauthorized to approve this trip");
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

    public void rejectCustomizedTrip(Integer boatOwnerId , Integer tripId){
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        Trip trip = tripRepository.findTripById(tripId);
        Customer customer = trip.getCustomer();
        if(boatOwner == null ){
            throw new ApiException("Boat owner not found");
        }
        if(!trip.getBoatOwner().equals(boatOwner)){
            throw new ApiException("Boat owner unauthorized to reject this trip");
        }

        tripRepository.delete(trip);

        String subject = "Apology Regarding Your Customized Trip Request ❗";

        String body =
                "Hello " + customer.getName() + ",\n\n" +
                        "We hope you are doing well.\n\n" +
                        "We regret to inform you that your request for the customized trip:\n" +
                        trip.getTitle() + "\n\n" +
                        "Could not be accepted at the moment ❗\n\n" +
                        "Trip Details:\n" +
                        "- Start: " + trip.getStartDate() + "\n" +
                        "- From: " + trip.getStartLocation() + "\n" +
                        "- To: " + trip.getDestinationLocation() + "\n\n" +
                        "We sincerely apologize for any inconvenience this may have caused.\n" +
                        "Please feel free to submit another request or contact us if you need further assistance.\n\n" +
                        "Thank you for your understanding 🙏";

        sendMailService.sendMessage(customer.getEmail(), subject, body);

    }

    public List<Trip> getTripsByStatus(String status){
        List<Trip> trips = tripRepository.findTripByStatus(status);
        if(trips.isEmpty()){
            throw new ApiException("There is no trips with the given status");
        }
        return trips;
    }

    public List<Trip> getTripsByDestinationLocation(String destinationLocation){
        List<Trip> trips = tripRepository.findTripByDestinationLocation(destinationLocation);
        if(trips.isEmpty()){
            throw new ApiException("There is no trips with the given destination location");
        }
        return trips;
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
                System.out.println("Times out"); //**   delete after testing
                //call emergency
                sendEmergency(tripId,false);
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

    public void sendEmergency(Integer tripId,boolean byUser) {
        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null) throw new ApiException("trip not found");

        Customer customer = trip.getCustomer();
        Emergency emergency = emergencyRepository.findEmergencyByCustomer_Id(customer.getId());
        BoatOwner boatOwner = trip.getBoatOwner();

        String tripInfo = "Trip Title: "+trip.getTitle() +"\n"+
         "Start Location: "+trip.getStartLocation()+" : "+geocodingService.getLocationCoordinates(trip.getStartLocation())+"\n" +
         "End Location: "+trip.getEndLocation()+"\n : "+geocodingService.getLocationCoordinates(trip.getEndLocation())+
          "Destination: "+trip.getDestinationLocation()+" : "+geocodingService.getLocationCoordinates(trip.getDestinationLocation())+"\n" +
           "expected Start and End time : "+trip.getStartDate()+"-"+trip.getEndDate()+"\n" +
            "Trip Type: "+trip.getTripType();



        String emergType = (byUser) ? "they asked for help" : "they didn't arrive/finish trip on time";
        String ownerMessageTitle = "*EMERGENCY CASE*";
        String ownerMessageBody = "An EMERGENCY case for one of your customers of trip ID "+tripId+"" +
         "\nwhere **"+emergType+"**\nTrip info :\n"+tripInfo;

        String contactMessageTitle = "*EMERGENCY CASE* for your relative "+customer.getName();
        String contactMessageBody = "Dear "+emergency.getName()+", you recived this message as an emergency contact for "+customer.getName()+"." +
         "\n They went on sea trip and "+emergType+"\nTrip info :\n"+tripInfo;


        sendMailService.sendMessage(boatOwner.getEmail(),ownerMessageTitle,ownerMessageBody);
        sendMailService.sendMessage(emergency.getEmail(),contactMessageTitle,contactMessageBody);
    }



    public void discountTripTillDate(Integer tripId, Integer ownerId, Double discountPrecentage, LocalDate date){
        Trip trip = tripRepository.findTripById(tripId);
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        if (trip == null || owner==null) throw new ApiException("trip or owner not found");

        if(owner!=trip.getBoatOwner())throw new ApiException("not Authorized");
        long discount = (long) (trip.getTotalPrice()-(trip.getTotalPrice()*discountPrecentage));
        long oldPrice = trip.getTotalPrice();
        trip.setTotalPrice(discount);
        tripRepository.save(trip);

        //timer
        Timer timer = new Timer();      //Do we cancel the timer when customer book the trip ?
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = date.atStartOfDay();
        long delayMillis = Duration.between(now, end).toMillis();
        if (delayMillis <= 0) throw new ApiException(""); ////**********

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Times out"); //delete after testing
               //price back
               trip.setTotalPrice(oldPrice);
               tripRepository.save(trip);
            }
        }, delayMillis * 1000);

    }


    public ApiResponse tripPreAskDate(Integer tripId, LocalDateTime start, LocalDateTime end){

    Trip trip = tripRepository.findTripById(tripId);
    if(trip==null) throw new ApiException("Trip not found");
    if(end.isBefore(start)) throw new ApiException("*end date must be after start date");



        Long DurationHours = Duration.between(start, end).toHours();
        Long newPrice = DurationHours * trip.getBoat().getPricePerHour();

        return new ApiResponse("The expected price is : "+newPrice);
    }


    public void tripAskDate(Integer tripId,Integer customerId,LocalDateTime start,LocalDateTime end){

        Customer customer = customerRepository.findCustomerById(customerId);
        Trip trip = tripRepository.findTripById(tripId);
        if(trip==null||customer==null) throw new ApiException("Trip or customer not found");
        if(end.isBefore(start)) throw new ApiException("*end date must be after start date");

        Long DurationHours = Duration.between(start, end).toHours();
        Long newPrice = DurationHours * trip.getBoat().getPricePerHour();
        String title = "Changing Trip Date Request";
        String body = "Request from the customer "+customer.getName()+"\n" +
                "To change date of trip "+trip.getTitle()+" #"+trip.getId()+"\n" +
                "To be  ["+start+"-"+end+"] after it was ["+trip.getStartDate()+"-"+trip.getEndDate()+"]\n" +
                "The new price : "+newPrice ;

        sendMailService.sendMessage(trip.getBoatOwner().getEmail(),title,body);

    }



    public Trip getTribByOwner(Integer owner){return tripRepository.findTripByBoatOwner_Id(owner);}
}
