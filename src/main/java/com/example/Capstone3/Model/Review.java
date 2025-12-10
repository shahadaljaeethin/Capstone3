package com.example.Capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "*enter rating")
    @Min(value = 1, message = "*rating must be at least 1")
    @Max(value = 5, message = "*rating must be at most 5")
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @NotEmpty(message = "*enter comment")
    @Size(max = 300, message = "*comment too long (max 300 characters)")
    @Column(columnDefinition = "varchar(300) not null")
    private String comment;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime creatAt = LocalDateTime.now();

    @ManyToOne
    private BoatOwner boatOwner;

    @ManyToOne
    private Customer customer;
}
