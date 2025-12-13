package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table
public class BoatOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "*enter full name")
    @Size(min = 4, max = 60, message = "*full name size 4-60 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "*full name can contains letters and spaces only")
    @Column(columnDefinition = "varchar(60) not null")
    private String fullName;

    @NotEmpty(message = "*enter username")
    @Size(min = 4, max = 25, message = "*username size 4-25 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._-]*$", message = "*username must start with letter and can contains only -_.")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String userName;

    @Size(max = 200, message = "*about too long (max 200 characters)")
    @Column(columnDefinition = "varchar(200)")
    private String about;

    @NotEmpty(message = "*enter password")
    @Size(min = 8, max = 30, message = "*password length 8-30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./_-])[A-Za-z\\d@$!%*?&./_-]{8,30}$", message = "*password must contain small & capital letter, digit and special character")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "*enter email")
    @Size(max = 45 , message = "*email too long")
    @Email
    @Column(columnDefinition = "varchar(45) not null unique")
    private String email;

    @NotEmpty(message = "*enter phone number")
    @Size(min = 10, max = 10, message = "*phone number must be 10 digits")
    @Pattern(regexp = "^05[0-9]{8}$", message = "*phone number must start with 05xxxxxx")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phone;

    @NotEmpty(message = "*enter license number")
    @Size(min = 4, max = 50, message = "*license number size 4-50 characters")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "*license number can contains letters, digits and - only")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String licenseNumber;

    @Pattern(regexp = "^(PENDING|ACTIVE)$", message = "*status must be PENDING, ACTIVE")
    @Column(columnDefinition = "varchar(20) not null default 'PENDING'")
    private String status = "PENDING";



    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Boat> boats ;

    //---------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "boatOwner")
    @JsonIgnore
    private Set<Trip> tripSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    @JsonIgnore
    private Set<DriverRequest> requests;


}
