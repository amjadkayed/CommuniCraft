package com.communicate_craft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Crafters")
@NoArgsConstructor
@AllArgsConstructor
public class Crafter implements Serializable {
    @Id
    @Column(name = "CrafterId", nullable = false)
    private Long crafterId;

    @Column(name = "Bio")
    private String bio;

    @Column(name = "IsAvailable")
    private Boolean isAvailable = true;

    @Column(name = "Rating")
    private Double crafterRating;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

}
