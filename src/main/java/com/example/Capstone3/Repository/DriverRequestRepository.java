package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.DriverRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRequestRepository extends JpaRepository<DriverRequest,Integer> {
    DriverRequest findDriverRequestById(Integer id);
}
