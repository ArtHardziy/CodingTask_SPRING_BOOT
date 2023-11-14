package com.expertsoft.phoneshop.persistence.repository;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneshopUserRepository extends JpaRepository<PhoneshopUser, Long> {

    Optional<PhoneshopUser> findByName(String username);

    Optional<PhoneshopUser> findByEmail(String email);
}
