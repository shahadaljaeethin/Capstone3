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
public class Boat {

    @Id
    private Integer id;

    @NotEmpty(message = "*enter boat name")
    @Size(min = 3, max = 50, message = "*name size 3-50 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "*name can contain letters, digits and spaces only")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotNull(message = "*enter capacity")
    @Min(value = 1, message = "*capacity must be at least 1")
    @Column(columnDefinition = "int not null")
    private Integer capacity;

    @NotNull(message = "*enter max fuel")
    @Positive(message = "*max fuel must be positive")
    @Column(columnDefinition = "double not null")
    private Double maxFuel;

    @NotNull(message = "*enter price per hour")
    @Positive(message = "*price per hour must be positive")
    @Column(columnDefinition = "int not null")
    private Integer pricePerHour;

    @NotEmpty(message = "*enter status")
    @Pattern(regexp = "^(AVAILABLE|NOT_AVAILABLE|MAINTENANCE)$", message = "*status must be AVAILABLE, NOT_AVAILABLE or MAINTENANCE")
    @Column(columnDefinition = "varchar(20) not null default 'AVAILABLE'")
    private String status = "AVAILABLE";

    @NotNull(message = "*select owner")
    @ManyToOne
    private BoatOwner owner;

    @NotNull(message = "*select category")
    @ManyToOne
    private Category category;

    @NotEmpty
    @Size(max = 300, message = "*description too long (max 300 characters)")
    @Column(columnDefinition = "varchar(300)")
    private String description;


    @OneToMany(mappedBy = "boat", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Review> reviews;


//--------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "boat")
    private Set<Trip> tripSet;

}
