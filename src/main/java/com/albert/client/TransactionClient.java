package com.albert.client;

import com.albert.domain.dto.TransactionDto;
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
public class TransactionClient {

    private final RestTemplate restTemplate;

    private static final String TRANSACTION_URL = "http://localhost:8888/v1/transaction";
    private static TransactionClient transactionClient;

    public static TransactionClient getInstance() {
        if (transactionClient == null) {
            transactionClient = new TransactionClient(new RestTemplate());
        }
        return transactionClient;
    }


    public TransactionDto getTransaction(Long transactionId) {
        URI uri = UriComponentsBuilder.fromUriString(TRANSACTION_URL + "/" + transactionId)
                .build().toUri();
        try {
            TransactionDto transaction = restTemplate.getForObject(uri, TransactionDto.class);
            return (Objects.requireNonNull(transaction));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void deleteTransaction(Long transactionId) {
        URI uri = UriComponentsBuilder.fromUriString(TRANSACTION_URL + "/" + transactionId)
                .build().toUri();
        try {
            restTemplate.delete(uri);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void createTransaction(TransactionDto transactionDto) {
        URI uri = UriComponentsBuilder.fromUriString(TRANSACTION_URL)
                .build().toUri();
        try {
            restTemplate.postForObject(uri, transactionDto, Void.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void updateTransaction(TransactionDto transactionDto) {
        URI uri = UriComponentsBuilder.fromUriString(TRANSACTION_URL)
                .build().toUri();
        try {
            restTemplate.put(uri, transactionDto);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);

        }
    }
}
