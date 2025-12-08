package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.BoatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner,Integer> {
    BoatOwner findBoatOwnersById(Integer id);
}
