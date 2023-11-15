package com.expertsoft.phoneshop.persistence.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Data
public class PhoneshopUserPrincipal implements OAuth2User, UserDetails {

    @Getter
    private Long id;
    @Getter
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    @Getter
    @Setter
    private Map<String, Object> attributes;

    public PhoneshopUserPrincipal(Long id,
                                  String name,
                                  String email,
                                  String password,
                                  Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public static PhoneshopUserPrincipal create(PhoneshopUser user) {
        List<? extends GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        if (user.getRole() != null) {
            authorities = user.getAuthorities().stream().toList();
        }

        return new PhoneshopUserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static PhoneshopUserPrincipal create(PhoneshopUser user, Map<String, Object> attributes) {
        PhoneshopUserPrincipal userPrincipal = PhoneshopUserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    @Override
    public String getUsername() {
        return this.name;
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

    @Override
    public String getName() {
        return String.valueOf(this.name);
    }
}
