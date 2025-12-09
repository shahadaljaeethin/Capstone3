package com.example.Capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;


    @NotEmpty(message = "Full name cannot be empty")
    @Size(min = 4, max = 60, message = "*Full name size 4-60 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "*Full name can contains letters and spaces only")
    @Column(columnDefinition = "varchar(60) not null")
    private String fullName;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 25, message = "*Username size 4-25 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._-]*$", message = "*Username must start with letter and can contains only -_.")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String userName;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 30, message = "*Password length 8-30 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./_-])[A-Za-z\\d@$!%*?&./_-]{8,30}$",
            message = "*Password must contain small & capital letter, digit and special character"
    )
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @CreationTimestamp
    @Column(columnDefinition = "dateTime ")
    private LocalDate createdAt;
}
