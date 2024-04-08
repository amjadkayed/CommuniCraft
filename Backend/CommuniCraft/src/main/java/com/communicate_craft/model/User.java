package com.communicate_craft.model;

import com.communicate_craft.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userID;

    @NotEmpty(message = "empty username")
    @Column(name = "Username")
    private String username;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "invalid first name")
    @NotEmpty(message = "empty first name")
    @Column(name = "FirstName")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "invalid last name")
    @NotEmpty(message = "empty last name")
    @Column(name = "LastName")
    private String lastName;

    @NotEmpty(message = "empty email")
    @Email
    @Column(name = "Email")
    private String email;

    @NotEmpty(message = "empty password")
    @Column(name = "PasswordHash")
    private String passwordHash;

    @NotEmpty(message = "empty phone number")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^\\d{10}$", message = "invalid phone number")
    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "UserType")
    private UserType userType;

    @Column(name = "UserImageURL")
    private String userImageURL;

    @Column(name = "SignUpDate", nullable = false, updatable = false)
    private LocalDateTime signUpDate;

    @Column(name = "LastOnlineTime", nullable = false)
    private LocalDateTime lastOnlineTime;


    @Column(name = "Rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "NumberOfReviews", nullable = false)
    private Integer numberOfReviews;

    @Column(name = "TotalSalary", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalSalary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_location_id")
    @JsonIgnore
    private Location location;

    public User(){
        totalSalary = BigDecimal.valueOf(0);
        numberOfReviews = 0;
        signUpDate = LocalDateTime.now();
        lastOnlineTime = LocalDateTime.now();
    }
}
