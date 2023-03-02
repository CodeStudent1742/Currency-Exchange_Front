package com.albert.client;

import com.albert.domain.dto.CartDto;
import com.albert.domain.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartClient {

    private final RestTemplate restTemplate;

    private static final String CART_URL = "http://localhost:8888/v1/cart";
    private static CartClient cartClient;

    public static CartClient getInstance() {
        if (cartClient == null) {
            cartClient = new CartClient(new RestTemplate());
        }
        return cartClient;
    }

    public CartDto getCart(Long cartId) {
        URI uriC = UriComponentsBuilder.fromUriString(CART_URL + "/" + cartId)
                .build().toUri();
        try {
            CartDto cart = restTemplate.getForObject(uriC, CartDto.class);
            return (Objects.requireNonNull(cart));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    public List<TransactionDto> getTransactionsInCart(Long cartId){
        URI uri = UriComponentsBuilder.fromUriString(CART_URL + "/" + cartId+ "/transactions" )
                .build().toUri();
        try {
            TransactionDto[] transactions = restTemplate.getForObject(uri, TransactionDto[].class);
            return Arrays.asList(Objects.requireNonNull(transactions));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void addTransactionToCart(Long cartId, Long transactionId) {
        URI uriCT = UriComponentsBuilder.fromUriString(CART_URL + "/" + cartId + "/" + transactionId)
                .build().toUri();
        try {
            restTemplate.put(uriCT,CartDto.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
    public void makeExchangeOrderFromCart(Long cartId) {
        URI uriCT = UriComponentsBuilder.fromUriString(CART_URL + "/order/" + cartId )
                .build().toUri();
        try {
            restTemplate.postForEntity(uriCT,null, Void.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
}


