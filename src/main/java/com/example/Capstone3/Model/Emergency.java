package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Emergency {

    @Id
    private Integer id;

    @NotEmpty(message = "*enter name")
    @Size(min = 4, max = 25, message = "*name size 4-25character")
    @Pattern(regexp = "^[A-Za-z]+$", message = "*name can contains letters only")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotEmpty(message = "*describe this emergency contact, for example : father")
    @Size(min = 3, max = 15, message = "*description size 4-15character")
    @Pattern(regexp = "^[A-Za-z]+$", message = "*description can contains letters only")
    @Column(columnDefinition = "varchar(15) not null")
    private String rel;

    @NotEmpty(message = "*enter phone number")
    @Size(min = 10, max = 10, message = "*phone number must be 10 digits")
    @Pattern(regexp = "^05[0-9]{8}$", message = "*phone number must start with 05xxxxxx")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty(message = "*enter email")
    @Size(max = 45, message = "*email too long")
    @Email
    @Column(columnDefinition = "varchar(45) not null")
    private String email;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Customer customer;
}
