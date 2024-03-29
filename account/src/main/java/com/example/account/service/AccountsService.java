package com.example.account.service;

import com.example.account.repository.AccountsRepository;
import com.example.account.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;


}
