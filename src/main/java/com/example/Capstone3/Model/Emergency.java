package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "*enter name")
    @Size(min = 4,max = 25 , message = "*name size 4-25character")
    @Pattern(regexp = "^[A-Za-z]+$",message = "*name can contains letters only")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotEmpty(message = "*describe this emergency contact, for example : father")
    @Size(min = 3,max = 15 , message = "*description size 4-15character")
    @Pattern(regexp = "^[A-Za-z]+$",message = "*description can contains letters only")
    @Column(columnDefinition = "varchar(15) not null")
    private String rel;

//-------------------------------------------
    @OneToOne
    @MapsId
    @JsonIgnore
    private Customer customer;
}
