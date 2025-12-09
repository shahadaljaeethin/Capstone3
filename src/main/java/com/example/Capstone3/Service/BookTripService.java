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

    public List<BookTrip> getBookTrip(){
        return bookTripRepository.findAll();
    }

    public void addBookTrip(BookTrip bookTrip){
        Trip trip = tripRepository.findTripById(bookTrip.getTrip().getId());
        Customer customer = customerRepository.findCustomerById(bookTrip.getCustomer().getId());
        if(trip == null || customer == null){
            throw new ApiException("Trip or customer not found");
        }
        if(trip.getStatus().equalsIgnoreCase("Ongoing") || trip.getStatus().equalsIgnoreCase("Completed")){
            throw new ApiException("You cannot boob this trip");
        }
        bookTripRepository.save(bookTrip);
    }

    public void updateBookTrip(Integer id, BookTrip bookTrip){
        BookTrip oldBookTrip = bookTripRepository.findBookTripById(id);
        if(oldBookTrip == null){
            throw new ApiException("Book Trip not found");
        }
        Trip trip = tripRepository.findTripById(bookTrip.getTrip().getId());
        Customer customer = customerRepository.findCustomerById(bookTrip.getCustomer().getId());
        if(trip == null || customer == null){
            throw new ApiException("Trip or customer not found");
        }
        oldBookTrip.setTrip(bookTrip.getTrip());
        oldBookTrip.setCustomer(bookTrip.getCustomer());
        bookTripRepository.save(oldBookTrip);
    }

    public void deleteBookTrip(Integer id){
        BookTrip bookTrip = bookTripRepository.findBookTripById(id);
        if(bookTrip == null){
            throw new ApiException("Book Trip not found");
        }
        bookTripRepository.delete(bookTrip);
    }
}
