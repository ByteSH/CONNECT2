package com.connect.CONNECT2.entity;

import com.connect.CONNECT2.enums.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "composer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "email_address", nullable = false, length = 150, unique = true)
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles role;

    @Column(name = "created_date")
    private LocalDate created;

    @OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Music> music = new ArrayList<>();
}
