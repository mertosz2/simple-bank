package com.example.account.repository;

import com.example.account.entity.Accounts;
import com.example.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>{

    Optional<Customer> findByMobileNumber(String mobileNumber);

}
