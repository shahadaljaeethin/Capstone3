package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Driver;
import com.example.Capstone3.Model.DriverRequest;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import com.example.Capstone3.Repository.DriverRepository;
import com.example.Capstone3.Repository.DriverRequestRepository;
import com.example.Capstone3.Repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverRequestService {

    private final DriverRequestRepository driverRequestRepository;
    private final DriverRepository driverRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final TripRepository tripRepository;

    public List<DriverRequest> getDriverRequests() {
        return driverRequestRepository.findAll();
    }

    public void addDriverRequest(Integer boatOwnerId, Integer driverId,Integer tripId ,DriverRequest driverRequest) {
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        Driver driver = driverRepository.findDriverById(driverId);
        Trip trip =  tripRepository.findTripById(tripId);
        if (boatOwner == null || driver == null || trip ==null) {
            throw new ApiException("Boat owner or driver not found");
        }
        if(!driver.getStatus().equals("available"))throw new ApiException("This driver is not available now");
        if(boatOwner!=trip.getBoatOwner())throw new ApiException("unAuthorized trip owner");
        if(trip.getDriver()!=null)throw new ApiException("this trip has a driver");


        driverRequest.setTrip(trip);
        driverRequest.setOwner(boatOwner);
        driverRequest.setDriver(driver);
        driverRequest.setStatus("pending");
        driverRequestRepository.save(driverRequest);

    }

    public void updateDriverRequest(Integer id, DriverRequest driverRequest) {
        DriverRequest oldDriverRequest = driverRequestRepository.findDriverRequestById(id);
        if (oldDriverRequest == null) {
            throw new ApiException("Driver request not found");
        }
        oldDriverRequest.setMessage(driverRequest.getMessage());
        oldDriverRequest.setDriver(driverRequest.getDriver());
        oldDriverRequest.setOwner(driverRequest.getOwner());
        driverRequestRepository.save(oldDriverRequest);
    }

    public void deleteDriverRequest(Integer id) {
        DriverRequest driverRequest = driverRequestRepository.findDriverRequestById(id);
        if (driverRequest == null) {
            throw new ApiException("Driver request not found");
        }
        driverRequestRepository.delete(driverRequest);
    }

    public void acceptRequest(Integer driverId, Integer requestId) {
        Driver driver = driverRepository.findDriverById(driverId);
        DriverRequest request = driverRequestRepository.findDriverRequestById(requestId);
        if (driver == null || request == null) {
            throw new ApiException("driver or request not found");
        }
        if (!driverId.equals(request.getDriver().getId())) {
            throw new ApiException("not authorized: this request belongs to another driver");
        }
        if (!"pending".equals(request.getStatus())) {
            throw new ApiException("this request has state of " + request.getStatus() + " already");
        }
        request.setStatus("accept");
        driverRequestRepository.save(request);
        driver.setStatus("busy");
        driverRepository.save(driver);
       request.getTrip().setDriver(driver);
       tripRepository.save(request.getTrip());


    }

    public void apologizeRequest(Integer driverId, Integer requestId) {
        Driver driver = driverRepository.findDriverById(driverId);
        DriverRequest request = driverRequestRepository.findDriverRequestById(requestId);
        if (driver == null || request == null) {
            throw new ApiException("driver or request not found");
        }
        if (!driverId.equals(request.getDriver().getId())) {
            throw new ApiException("not authorized: this request belongs to another driver");
        }
        if (!"pending".equals(request.getStatus())) {
            throw new ApiException("this request has state of " + request.getStatus() + " already");
        }
        request.setStatus("apologize");
        driverRequestRepository.save(request);

    }
}
