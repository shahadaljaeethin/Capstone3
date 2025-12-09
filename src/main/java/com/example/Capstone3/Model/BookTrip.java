package com.example.Capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "*enter status")
    @Pattern(regexp = "^(PENDING|ACCEPTED|REJECTED)$", message = "status must be PENDING, ACCEPTED or REJECTED")
    @Column(columnDefinition = "varchar(10) not null default 'PENDING'")
    private String status = "PENDING";


    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Customer customer;

}
