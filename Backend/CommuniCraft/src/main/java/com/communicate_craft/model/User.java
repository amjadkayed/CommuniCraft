package com.communicate_craft.model;

import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "UserID")
    private Long userID;

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
    @JsonIgnoreProperties
    private String email;

    @NotEmpty(message = "empty password")
    @Column(name = "Password")
    private String password;

    @NotEmpty(message = "empty phone number")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^\\d{10}$", message = "invalid phone number")
    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role;

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

    @ManyToOne
    @JoinColumn(name = "location_location_id")
    private Location location;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private transient Crafter crafter;

    @ManyToMany(mappedBy = "chatParticipants")
    @JsonIgnore
    private Set<Chat> chats = new LinkedHashSet<>();

    public User() {
        totalSalary = BigDecimal.valueOf(0);
        numberOfReviews = 0;
        signUpDate = LocalDateTime.now();
        lastOnlineTime = LocalDateTime.now();
    }

    public User(RegisterRequest registrationDTO, Location location) {
        this();
        setUsername(registrationDTO.getUsername());
        setFirstName(registrationDTO.getFirstName());
        setLastName(registrationDTO.getLastName());
        setEmail(registrationDTO.getEmail());
        setPassword(registrationDTO.getPassword());
        setPhoneNumber(registrationDTO.getPhoneNumber());
        setLocation(location);
        setRole(registrationDTO.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //----------------- to make password, email deserializable but not serializable

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", userImageURL='" + userImageURL + '\'' +
                ", signUpDate=" + signUpDate +
                ", lastOnlineTime=" + lastOnlineTime +
                ", rating=" + rating +
                ", numberOfReviews=" + numberOfReviews +
                ", totalSalary=" + totalSalary +
                ", location=" + location +
                ", crafter=" + crafter +
                '}';
    }
}
