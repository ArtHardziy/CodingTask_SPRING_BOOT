package com.expertsoft.phoneshop.persistence.model;

import com.expertsoft.phoneshop.persistence.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.expertsoft.phoneshop.persistence.Permission.*;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER(Set.of(
            READ
    )),
    ADMIN(
            Set.of(
                    READ, WRITE, DELETE
            )
    ),
    MANAGER(
            Set.of(
                    READ, WRITE
            )
    );

    private final Set<Permission> permissionSet;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissionSet()
                .stream()
                .map(Permission::getAuthority)
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public String[] getStringAuthorities() {
        return this.getAuthorities().stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

}