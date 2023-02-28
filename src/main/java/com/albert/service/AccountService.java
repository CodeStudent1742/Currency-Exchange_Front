package com.albert.service;

import com.albert.client.AccountClient;
import com.albert.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountClient accountClient;
    private static AccountService accountService;

    public static AccountService getInstance() {
        if (accountService == null) {
            accountService = new AccountService(AccountClient.getInstance());
        }
        return accountService;
    }

    public AccountDto getAccount(Long accountId) {
       return accountClient.getAccountDto(accountId);
    }
    public void putIntoAccount(Long accountId, String currency, Double value){
        accountClient.putIntoAccount(accountId,currency,value);
    }
    public void withdrawFromAccount(Long accountId, String currency, Double value){
        accountClient.withdrawFromAccount(accountId,currency,value);
    }
}
