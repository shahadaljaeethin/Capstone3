package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Model.Emergency;
import com.example.Capstone3.Repository.CustomerRepository;
import com.example.Capstone3.Repository.EmergencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyService {
    private final EmergencyRepository emergencyRepository;
    private final CustomerRepository customerRepository;


    public List<Emergency> getAll(){return emergencyRepository.findAll();}

    public void addEmergency(Integer customerId,Emergency emergency){
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer==null) {
            throw new ApiException("customer not found");
        }
        emergency.setCustomer(customer);
        emergencyRepository.save(emergency);
    }

    public void updateEmergency(Integer id,Emergency edit){

        Emergency emergency = emergencyRepository.findEmergencyById(id);
        if(emergency==null) throw new ApiException("emergency contact not found");
        emergency.setName(edit.getName());
       // emergency.setRel(edit.getDescription());
        //emergency.setLastKnownLocation(edit.getLastKnownLocation());
        emergencyRepository.save(emergency);

    }

    public void deleteEmergency(Integer id){
        Emergency emergency = emergencyRepository.findEmergencyById(id);
        if(emergency==null) throw new ApiException("emergeny contact not found");
        emergencyRepository.delete(emergency);
    }
}
