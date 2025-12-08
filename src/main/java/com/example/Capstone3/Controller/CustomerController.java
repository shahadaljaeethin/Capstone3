package com.example.Capstone3.Controller;

import com.example.Capstone3.Api.ApiResponse;
import com.example.Capstone3.Model.Customer;
import com.example.Capstone3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered successfully :)"));
    }


    @GetMapping("/get")
    public ResponseEntity<?> getAllCustomers() {
         return ResponseEntity.status(200).body(customerService.getAll());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody @Valid Customer customer) {
        customerService.updateCustomer(id, customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully :)"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Customer removed successfully :("));

    }

}
