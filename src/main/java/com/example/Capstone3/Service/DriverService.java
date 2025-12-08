package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Driver;
import com.example.Capstone3.Repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public List<Driver> getAll(){return driverRepository.findAll();}
    public void addDriver(Driver driver){
        driver.setStatus("pending");
        driverRepository.save(driver);
    }
    public void updateDriver(Integer id,Driver edit){
        Driver driver = driverRepository.findDriverById(id);
        if(driver==null) throw new ApiException("driver is not found");

        driver.setName(edit.getName());
        driver.setEmail(edit.getEmail());
        driver.setPassword(edit.getPassword());
        driver.setPhoneNumber(edit.getPhoneNumber());
        driver.setExperienceYears(edit.getExperienceYears());
        driver.setLicenseNumber(edit.getLicenseNumber());
        driver.setUsername(edit.getUsername());

        driverRepository.save(driver);
    }

    public void deleteDriver(Integer id){
        Driver driver = driverRepository.findDriverById(id);
        if(driver==null) throw new ApiException("driver is not found");
        driverRepository.delete(driver);
    }
//      ========================================================================================================== E E P
}
