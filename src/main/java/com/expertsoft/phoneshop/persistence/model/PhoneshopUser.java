package com.expertsoft.phoneshop.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
public class PhoneshopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getRoleType().getAuthorities().stream().toList();
    }
}
