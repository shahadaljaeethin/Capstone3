package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {
    Driver findDriverById(Integer id);
}
