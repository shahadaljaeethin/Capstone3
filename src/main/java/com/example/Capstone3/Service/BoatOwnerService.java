package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Admin;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Repository.AdminRepository;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoatOwnerService {

    private final BoatOwnerRepository boatOwnerRepository;
    private final AdminRepository adminRepository;

    public List<BoatOwner> getOwners(){
        return boatOwnerRepository.findAll();
    }

    public void addOwner(BoatOwner boatOwner){
        boatOwner.setStatus("PENDING");
        boatOwnerRepository.save(boatOwner);
    }

    public void updateOwner(Integer id, BoatOwner boatOwner){
        BoatOwner oldOwner = boatOwnerRepository.findBoatOwnerById(id);

        if(oldOwner == null){
            throw new ApiException("Owner not found");
        }

        oldOwner.setFullName(boatOwner.getFullName());
        oldOwner.setUserName(boatOwner.getUserName());
        oldOwner.setAbout(boatOwner.getAbout());
        oldOwner.setPassword(boatOwner.getPassword());
        oldOwner.setPhone(boatOwner.getPhone());
        oldOwner.setLicenseNumber(boatOwner.getLicenseNumber());
        oldOwner.setStatus(boatOwner.getStatus());

        boatOwnerRepository.save(oldOwner);
    }

    public void deleteOwner(Integer id){
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(id);

        if(owner == null){
            throw new ApiException("Owner not found");
        }

        boatOwnerRepository.delete(owner);
    }

    public void activateAccount(Integer adminId , Integer boatOwnerId){
        Admin admin = adminRepository.findAdminById(adminId);
        BoatOwner boatOwner = boatOwnerRepository.findBoatOwnerById(boatOwnerId);
        if(admin == null || boatOwner == null){
            throw new ApiException("admin or boat owner not found");
        }
        boatOwner.setStatus("ACTIVE");
        boatOwnerRepository.save(boatOwner);
    }
}
