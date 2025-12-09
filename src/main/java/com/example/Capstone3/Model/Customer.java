package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

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

    @PastOrPresent
    private LocalDate registerDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    @PrimaryKeyJoinColumn
    private Emergency contact;


    //---------------------------------------------------

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "customer")
    @JsonIgnore
    private Set<BookTrip> bookTripSet;

}
