package com.example.Capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "*enter message to the driver")
    @Size(min = 8, max = 400,message = "*message length range 8-400character")
    private String message;

    
     private String status;

    @ManyToOne
        private BoatOwner owner;

    @ManyToOne
    private Driver driver;


}
