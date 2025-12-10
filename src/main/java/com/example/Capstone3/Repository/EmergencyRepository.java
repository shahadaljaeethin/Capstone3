package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency,Integer> {
    Emergency findEmergencyById(Integer id);

    Emergency findEmergencyByCustomer_Id(Integer customerId);
}
