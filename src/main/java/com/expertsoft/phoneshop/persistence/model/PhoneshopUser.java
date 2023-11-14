package com.expertsoft.phoneshop.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;

@Data
@Entity
public class PhoneshopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @Column(unique = true, nullable = false)
    private String name;
    private String bio;
    private String avatar_url;
    private String location;
    private String company;
    @JsonIgnore
    private String password;
    @Column(unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private boolean enabled;
}
