package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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

    @NotEmpty(message = "Description cannot be empty")
    @Column(columnDefinition = "varchar(400) not null")
    private String description;

    @NotEmpty(message = "Trip type cannot be empty")
    @Pattern(regexp = "^(Fishing|Recreational)$", message = "Trip type must be either Fishing trip or Recreational trip")
    @Column(columnDefinition = "varchar(80) not null")
    private String tripType;

    @NotNull(message = "Fishing gear cannot be empty")
    @Column(columnDefinition = "TINYINT not null")
    private Boolean fishingGear;

    @NotNull(message = "Start date cannot be empty" )
    @Column(columnDefinition = "dateTime not null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be empty" )
    @Column(columnDefinition = "dateTime not null")
    private LocalDateTime endDate;

    @NotEmpty(message = "Start location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String startLocation;

    @NotEmpty(message = "Destination location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String destinationLocation;

    @NotEmpty(message = "End location cannot be empty" )
    @Column(columnDefinition = "varchar(90) not null")
    private String endLocation;

    @Pattern(regexp = "^(Upcoming|Ongoing|Completed|Request)$", message = "Status must be either Upcoming, Ongoing , Completed or Request")
    @Column(columnDefinition = "varchar(10) ")
    private String status;

    @Column(columnDefinition = "bigint")
    private Long totalPrice;


    private  Boolean isRequested;

//---------------------Relations-------------------------------

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


    @ManyToOne
    private  Customer customer;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @JsonIgnore
    private Set<DriverRequest> driverRequests;
}
