package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Model.BoatOwner;
import com.example.Capstone3.Model.Category;
import com.example.Capstone3.Repository.BoatOwnerRepository;
import com.example.Capstone3.Repository.BoatRepository;
import com.example.Capstone3.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoatService {

    private final BoatRepository boatRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final CategoryRepository categoryRepository;


    public List<Boat> getAllBoats() {
        return boatRepository.findAll();
    }

    public void addBoat(Integer ownerId, Integer categoryId, Boat boat) {

        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        Category category = categoryRepository.findCategoryById(categoryId);

        if (owner == null || category == null) {
            throw new ApiException("Owner or Category not found");
        }

        boat.setOwner(owner);
        boat.setCategory(category);


        boatRepository.save(boat);
    }

    public void updateBoat(Integer boatId, Integer ownerId, Integer categoryId, Boat boat) {

        Boat oldBoat = boatRepository.findBoatById(boatId);
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(ownerId);
        Category category = categoryRepository.findCategoryById(categoryId);

        if (oldBoat == null || owner == null || category == null) {
            throw new ApiException("Boat or Owner or Category not found");
        }

        oldBoat.setName(boat.getName());
        oldBoat.setCapacity(boat.getCapacity());
        oldBoat.setMaxFuel(boat.getMaxFuel());
        oldBoat.setPricePerHour(boat.getPricePerHour());
        oldBoat.setDescription(boat.getDescription());

        if (boat.getStatus() != null) {
            oldBoat.setStatus(boat.getStatus());
        }

        oldBoat.setOwner(owner);
        oldBoat.setCategory(category);

        boatRepository.save(oldBoat);
    }

    public void deleteBoat(Integer id) {
        Boat boat = boatRepository.findBoatById(id);
        if (boat == null) {
            throw new ApiException("Boat not found");
        }
        boatRepository.delete(boat);
    }

    public void updateBoatStatus(Integer boatId, String status) {
        Boat boat = boatRepository.findBoatById(boatId);

        if (boat == null) {
            throw new ApiException("Boat not found");
        }

        if (!status.matches("AVAILABLE|NOT_AVAILABLE|MAINTENANCE")) {
            throw new ApiException("Invalid status value");
        }

        boat.setStatus(status);
        boatRepository.save(boat);
    }
}
