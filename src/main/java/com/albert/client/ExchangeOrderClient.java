package com.albert.client;

import com.albert.domain.dto.ExchangeOrderDto;
import com.albert.domain.dto.NewExchangeOrderDto;
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
public class ExchangeOrderClient {

    private final RestTemplate restTemplate;
    private static final String ORDER_URL = "http://localhost:8888/v1/exchange";
    private static ExchangeOrderClient exchangeOrderClient;

    public static ExchangeOrderClient getInstance() {
        if (exchangeOrderClient == null) {
            exchangeOrderClient = new ExchangeOrderClient(new RestTemplate());
        }
        return exchangeOrderClient;
    }
    public ExchangeOrderDto getExchangeOrder(Long exchangeOrderId){
        URI uri = UriComponentsBuilder.fromUriString(ORDER_URL + "/" + exchangeOrderId)
                .build().toUri();
        try {
            ExchangeOrderDto exchangeOrderDto = restTemplate.getForObject(uri, ExchangeOrderDto.class);
            return (Objects.requireNonNull(exchangeOrderDto));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    public void createExchangeOrder(NewExchangeOrderDto newExchangeOrderDto){
        URI uri = UriComponentsBuilder.fromUriString(ORDER_URL)
                .build().toUri();
        try {
            restTemplate.postForObject(uri, newExchangeOrderDto, Void.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
    public void deleteExchangeOrder(Long exchangeOrderId){
        URI uri = UriComponentsBuilder.fromUriString(ORDER_URL+ "/" + exchangeOrderId)
                .build().toUri();
        try {
            restTemplate.delete(uri);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
}
