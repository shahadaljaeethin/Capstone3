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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "*enter category name")
    @Size(min = 3, max = 25, message = "*name size 3-25 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "*name can contains letters and spaces only")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotNull(message = "*enter range in km")
    @PositiveOrZero(message = "*range must be zero or positive")
    @Column(columnDefinition = "int not null")
    private Integer rangeKm;

    @Size(max = 200, message = "*description too long (max 200 characters)")
    @Column(columnDefinition = "varchar(200)")
    private String description;

    @Size(max = 20, message = "*size too long (max 20 characters)")
    @Column(columnDefinition = "varchar(20)")
    private String size;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Boat> boats ;
//--


}
