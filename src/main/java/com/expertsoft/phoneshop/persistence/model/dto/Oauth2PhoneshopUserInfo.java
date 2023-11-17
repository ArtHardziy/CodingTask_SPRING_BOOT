package com.expertsoft.phoneshop.persistence.model.dto;

import com.expertsoft.phoneshop.persistence.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Oauth2PhoneshopUserInfo {

    private String id;
    private Set<Authority> authorities;

    private String username;
    private String name;
    private String bio;
    private String avatar_url;
    private String location;
    private String company;
    private String password;
    private String email;
    private String provider;
    private String providerId;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
