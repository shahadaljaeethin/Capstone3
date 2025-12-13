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


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DriverRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "*enter message to the driver")
    @Size(min = 8, max = 400,message = "*message length range 8-400character")
    private String message;

    @Pattern(regexp = "^(pending|accept|apologize)$")
    @Column(columnDefinition = "varchar(15) not null default 'pending'")
    private String status = "pending";


    @ManyToOne
    @JsonIgnore
    private BoatOwner owner;

    @ManyToOne
    private Driver driver;


    @ManyToOne
    private Trip trip;

}
