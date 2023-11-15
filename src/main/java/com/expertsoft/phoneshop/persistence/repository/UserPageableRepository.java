package com.expertsoft.phoneshop.persistence.repository;

import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPageableRepository extends PagingAndSortingRepository<PhoneshopUser, Long> {

    //  Optional<PhoneshopUser> findByLogin(String login);

}
