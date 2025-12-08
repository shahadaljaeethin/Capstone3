package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat,Integer> {

    Boat findBoatById(Integer id);





}
