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

    public List<Boat> getBoats(){
        return boatRepository.findAll();
    }

    public void addBoat(Boat boat){
        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(boat.getOwner().getId());
        Category category = categoryRepository.findCategoryById(boat.getCategory().getId());

        if(owner == null || category == null){
            throw new ApiException("Owner or Category not found");
        }

        boatRepository.save(boat);
    }

    public void updateBoat(Integer id, Boat boat){
        Boat oldBoat = boatRepository.findBoatById(id);

        if(oldBoat == null){
            throw new ApiException("Boat not found");
        }

        BoatOwner owner = boatOwnerRepository.findBoatOwnerById(boat.getOwner().getId());
        Category category = categoryRepository.findCategoryById(boat.getCategory().getId());

        if(owner == null || category == null){
            throw new ApiException("Owner or Category not found");
        }

        oldBoat.setName(boat.getName());
        oldBoat.setCapacity(boat.getCapacity());
        oldBoat.setMaxFuel(boat.getMaxFuel());
        oldBoat.setPricePerHour(boat.getPricePerHour());
        oldBoat.setStatus(boat.getStatus());
        oldBoat.setOwner(boat.getOwner());
        oldBoat.setCategory(boat.getCategory());

        boatRepository.save(oldBoat);
    }

    public void deleteBoat(Integer id){
        Boat boat = boatRepository.findBoatById(id);

        if(boat == null){
            throw new ApiException("Boat not found");
        }

        boatRepository.delete(boat);
    }
}
