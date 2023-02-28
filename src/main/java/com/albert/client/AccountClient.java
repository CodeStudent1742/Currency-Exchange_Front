package com.albert.client;

import com.albert.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestTemplate restTemplate;

    private static final String ACCOUNT_URL = "http://localhost:8888/v1/account";
    private static AccountClient accountClient;

    public static AccountClient getInstance() {
        if (accountClient == null) {
            accountClient = new AccountClient(new RestTemplate());
        }
        return accountClient;
    }

    public AccountDto getAccountDto(Long accountId){
        URI uriA = UriComponentsBuilder.fromUriString(ACCOUNT_URL + "/"+accountId)
                .build().toUri();
        try {
            AccountDto account = restTemplate.getForObject(uriA, AccountDto.class);
            return (Objects.requireNonNull(account));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    //putIntoAccount
    public void  putIntoAccount(Long accountId, String currency,Double value){
        URI uri = UriComponentsBuilder.fromUriString(ACCOUNT_URL + "/"+accountId + "/put")
                .queryParam("currency", currency)
                .queryParam("value", value)
                .build().toUri();
        try {
            restTemplate.put(uri, AccountDto.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);

        }
    }
    public void withdrawFromAccount(Long accountId, String currency,Double value){
        URI uri2 = UriComponentsBuilder.fromUriString(ACCOUNT_URL + "/"+accountId + "/withdraw")
                .queryParam("currency", currency)
                .queryParam("value", value)
                .build().toUri();
        try {
            restTemplate.put(uri2, AccountDto.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }

}
