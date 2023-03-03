package com.albert.service;

import com.albert.client.CartClient;
import com.albert.domain.dto.CartDto;
import com.albert.domain.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartClient cartClient;
    private static CartService cartService;

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService(CartClient.getInstance());
        }
        return cartService;
    }

    public CartDto getCart(Long cartId){
        return cartClient.getCart(cartId);
    }
    public void addTransactionToCart(Long cartId, Long transactionId){
        cartClient.addTransactionToCart(cartId,transactionId);
    }
    public List<TransactionDto> getTransactionInCart(Long cartId){
        return cartClient.getTransactionsInCart(cartId);
    }
    public void makeExchangeOrderFromCart(Long cartId){
        cartClient.makeExchangeOrderFromCart(cartId);
    }
    public void removeTransactionFromCart(Long cartId, Long transactionId){
        cartClient.deleteTransactionFromCart(cartId,transactionId);
    }
}
