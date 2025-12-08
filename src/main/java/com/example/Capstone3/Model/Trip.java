package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String title;

    @NotEmpty(message = "Full name cannot be empty")
    @Pattern(regexp = "^(Fishing trip | Recreational trip)$", message = "Trip type must be either Fishing trip or Recreational trip")
    @Column(columnDefinition = "varchar(80) not null")
    private String tripType;

    @NotEmpty(message = "Fishing gear cannot be empty")
    @Column(columnDefinition = "TINYINT not null")
    private boolean fishingGear;

    @NotEmpty(message = "Start date cannot be empty" )
    @Column(columnDefinition = "dateTime not null")
    private LocalDate startDate;

    @NotEmpty(message = "End date cannot be empty" )
    @Column(columnDefinition = "dateTime not null")
    private LocalDate endDate;

    @NotEmpty(message = "Start location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String startLocation;

    @NotEmpty(message = "Destination location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String destinationLocation;

    @NotEmpty(message = "End location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String endLocation;

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^(Upcoming | Ongoing | Completed )$", message = "Status must be either Upcoming, Ongoing or Completed")
    @Column(columnDefinition = "varchar(10) not null")
    private String status;

    @NotNull(message = "Total price cannot be null")
    @Column(columnDefinition = "Double not null")
    private Double totalPrice;

//---------------------Relation-----------------------------

    @ManyToOne
    @JoinColumn
    private Driver driver;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @JsonIgnore
    private Set<BookTrip> bookTripSet;

    @ManyToOne
    @JsonIgnore
    private Boat boat;

    @ManyToOne
    @JsonIgnore
    private BoatOwner boatOwner;

}
