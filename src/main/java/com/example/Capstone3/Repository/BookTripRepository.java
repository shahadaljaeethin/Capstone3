package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.BookTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTripRepository extends JpaRepository<BookTrip, Integer> {

    BookTrip findBookTripById(Integer id);

    List<BookTrip> findBookTripByStatusAndTrip_Id(String status,Integer tripId);
}
