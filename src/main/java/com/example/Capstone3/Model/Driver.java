package com.example.Capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "*enter name")
    @Size(min = 4,max = 25 , message = "*name size 4-25character")
    @Pattern(regexp = "^[A-Za-z]+$",message = "*name can contains letters only")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotEmpty(message = "*enter username")
    @Size(min = 4,max = 25 , message = "*username size 4-25character")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._-]*$",message = "*username must starts with letter and can contains these special characters only : -_. ")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String username;

    @NotEmpty(message = "*enter password")
    @Size(min = 8,max = 30 , message = "*password length size 8-30character")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./_-])[A-Za-z\\d@$!%*?&./_-]{8,30}$",message = "*password must contains at least one small and capital letter, one digit and special character")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "*enter email")
    @Size(max = 45 , message = "*email too long")
    @Email
    @Column(columnDefinition = "varchar(45) not null unique")
    private String email;

    @NotEmpty(message = "*enter phone number")
    @Size(min = 10,max = 10 , message = "*phone number must be 10 digits")
    @Pattern(regexp = "^05[0-9]{8}$",message = "*phone number must start with 05xxxxxx")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;

    @NotEmpty(message = "*enter license number")
    @Size(min = 5,max = 10 , message = "*license number must be 5-10 digits")
    @Pattern(regexp = "^[0-9]$",message = "*license number contains digits only")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String licenseNumber;

    @NotNull(message = "*enter experience years")
    @Min(value = 0,message = "*enter valid number")
    @Max(value = 45, message = "*enter valid number")
    @PositiveOrZero
    private Integer experienceYears;


    @NotNull(message = "*enter hourly wage for your work")
    @Min(value = 10,message = "*enter valid number")
    @Max(value = 1000, message = "*enter valid number")
    @PositiveOrZero
    private Double hourlyWage;

    @Pattern(regexp = "^(pending|available|busy)$")  // new attribute
    private String status;

    @NotEmpty(message = "*enter city")
    private String city;


    //---------------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "driver")
    private Set<Trip> trips;

}
