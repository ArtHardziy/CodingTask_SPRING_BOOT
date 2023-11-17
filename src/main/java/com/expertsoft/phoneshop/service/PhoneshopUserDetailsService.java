package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import com.expertsoft.phoneshop.persistence.model.PhoneshopUserPrincipal;
import com.expertsoft.phoneshop.persistence.repository.PhoneshopUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class PhoneshopUserDetailsService implements UserDetailsService {

    private PhoneshopUserRepository phoneshopUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PhoneshopUser phoneshopUser = phoneshopUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with email %s not found", email
                )));
        return PhoneshopUserPrincipal.create(phoneshopUser);
    }
}
