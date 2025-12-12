package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.BookTrip;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.BookTripRepository;
import com.example.Capstone3.Repository.CustomerRepository;
import com.example.Capstone3.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookTripService {

    private final BookTripRepository bookTripRepository;
    private final TripRepository tripRepository;
    private final CustomerRepository customerRepository;
    private final SendMailService sendMailService;

    public List<BookTrip> getBookTrip() {
        return bookTripRepository.findAll();
    }

    public void addBookTrip(Integer tripId, Integer customerId) {

        Trip trip = tripRepository.findTripById(tripId);
        Customer customer = customerRepository.findCustomerById(customerId);

        if (trip == null || customer == null) {
            throw new ApiException("Trip or customer not found");
        }

        if (trip.getStatus().equalsIgnoreCase("Ongoing") ||
                trip.getStatus().equalsIgnoreCase("Completed")) {
            throw new ApiException("You cannot book this trip");
        }

        if(customer.getContact()==null) throw new ApiException("You must have emergency contact to book trip");

        BookTrip bookTrip = new BookTrip();
        bookTrip.setTrip(trip);
        bookTrip.setCustomer(customer);

        bookTripRepository.save(bookTrip);
    }


    public void deleteBookTrip(Integer id) {

        BookTrip bookTrip = bookTripRepository.findBookTripById(id);

        if (bookTrip == null) {
            throw new ApiException("Book Trip not found");
        }

        bookTripRepository.delete(bookTrip);
    }

    public void acceptBooking(Integer ownerId, Integer bookTripId) {

        BookTrip bookTrip = bookTripRepository.findBookTripById(bookTripId);

        if (bookTrip == null) {
            throw new ApiException("Booking not found");
        }

        Trip trip = bookTrip.getTrip();

        if (trip == null || trip.getBoatOwner() == null) {
            throw new ApiException("Trip or owner not found");
        }

        if (!trip.getBoatOwner().getId().equals(ownerId)) {
            throw new ApiException("You are not allowed to accept this booking");
        }

        if ("ACCEPTED".equals(bookTrip.getStatus())) {
            throw new ApiException("Booking already accepted");
        }

        Customer customer = bookTrip.getCustomer();
        trip.setCustomer(customer);
        tripRepository.save(trip);

        bookTrip.setStatus("ACCEPTED");
        bookTripRepository.save(bookTrip);



        if (customer != null && customer.getEmail() != null) {

            String subject = "Booking Accepted ✅";

            String body =
                    "Hello " + customer.getName() + ",\n\n" +
                            "Your booking for the trip:\n" +
                            trip.getTitle() + "\n\n" +
                            "Has been ACCEPTED successfully ✅\n\n" +
                            "Trip Details:\n" +
                            "- Start: " + trip.getStartDate() + "\n" +
                            "- From: " + trip.getStartLocation() + "\n" +
                            "- To: " + trip.getDestinationLocation() + "\n\n" +
                            "Enjoy your trip 🌊🚤";

            sendMailService.sendMessage(customer.getEmail(), subject, body);
        }
    }

    public void rejectBooking(Integer ownerId, Integer bookTripId) {

        BookTrip bookTrip = bookTripRepository.findBookTripById(bookTripId);

        if (bookTrip == null) {
            throw new ApiException("Booking not found");
        }

        Trip trip = bookTrip.getTrip();

        if (trip == null || trip.getBoatOwner() == null) {
            throw new ApiException("Trip or owner not found");
        }

        if (!trip.getBoatOwner().getId().equals(ownerId)) {
            throw new ApiException("You are not allowed to reject this booking");
        }

        if ("REJECTED".equals(bookTrip.getStatus())) {
            throw new ApiException("Booking already rejected");
        }

        bookTrip.setStatus("REJECTED");
        bookTripRepository.save(bookTrip);

        Customer customer = bookTrip.getCustomer();

        if (customer != null && customer.getEmail() != null) {

            String subject = "Booking Rejected ❌";

            String body =
                    "Hello " + customer.getName() + ",\n\n" +
                            "Unfortunately, your booking for the trip:\n" +
                            trip.getTitle() + "\n\n" +
                            "Has been REJECTED ❌\n\n" +
                            "You can try again with another trip.\n\n" +
                            "Thank you for using our system.";

            sendMailService.sendMessage(customer.getEmail(), subject, body);
        }
    }



    public List<BookTrip> getPendingRequestForTrip(Integer tripId){
        return bookTripRepository.findBookTripByStatusAndTrip_Id("PENDING",tripId);
    }

    public List<BookTrip> getMyBookings(Integer customerId) {
        List<BookTrip> bookings = bookTripRepository.findAllByCustomer_Id(customerId);
        if (bookings.isEmpty()) {
            throw new ApiException("No bookings found for this customer");
        }
        return bookings;
    }

}
