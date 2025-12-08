package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    //-

    public void addCustomer(Customer customer){
        customer.setRegisterDate(LocalDate.now());
        customerRepository.save(customer);
                                             }


    public List<Customer> getAll(){
        return customerRepository.findAll();}

    public void updateCustomer(Integer id,Customer edit){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer==null) throw new ApiException("customer not found");
//      ------------------------------------------------------------------

        customer.setName(edit.getName());
        customer.setUsername(edit.getUsername());
        customer.setPhoneNumber(edit.getPhoneNumber());
        customer.setEmail(edit.getEmail());
        customer.setPassword(edit.getPassword());

        customerRepository.save(customer);
     }

    public void deleteCustomer(Integer id){

        Customer customer = customerRepository.findCustomerById(id);
        if(customer==null) throw new ApiException("customer not found");

        customerRepository.delete(customer);
           }



//=========================================================================(( EXTRA END POINTS ))===================

}
