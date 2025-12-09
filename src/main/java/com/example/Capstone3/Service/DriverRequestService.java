package com.example.Capstone3.Service;

import com.example.Capstone3.Repository.DriverRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRequestService {
    private final DriverRequestRepository driverRequestRepository;







    //=========================================[ Extra End Point ]===============================\\



    public void acceptRequest(Integer driverId,Integer requestId){

    
    }

    public void appologizeRequest(){}
}
