package com.albert.service;

import com.albert.client.ExchangeOrderClient;
import com.albert.domain.dto.ExchangeOrderDto;
import com.albert.domain.dto.NewExchangeOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeOrderService {

    private final ExchangeOrderClient exchangeOrderClient;
    private static ExchangeOrderService exchangeOrderService;

    public static ExchangeOrderService getInstance() {
        if (exchangeOrderService == null) {
            exchangeOrderService = new ExchangeOrderService(ExchangeOrderClient.getInstance());
        }
        return exchangeOrderService;
    }

    public ExchangeOrderDto getExchangeOrder(Long exchangeOrderId){
        return exchangeOrderClient.getExchangeOrder(exchangeOrderId);
    }
    public void createExchangeOrder(NewExchangeOrderDto newExchangeOrderDto){
        exchangeOrderClient.createExchangeOrder(newExchangeOrderDto);
    }
    public void deleteExchangeOrder(Long exchangeOrderId){
        exchangeOrderClient.deleteExchangeOrder(exchangeOrderId);
    }
}
