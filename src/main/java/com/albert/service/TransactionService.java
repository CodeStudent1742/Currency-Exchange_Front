package com.albert.service;


import com.albert.client.TransactionClient;
import com.albert.domain.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionClient transactionClient;
    private static TransactionService transactionService;

    public static TransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionService(TransactionClient.getInstance());
        }
        return transactionService;
    }

    public TransactionDto getTransaction(Long transactionId){
      return  transactionClient.getTransaction(transactionId);
    }
    public void createTransaction(TransactionDto transactionDto){
        transactionClient.createTransaction(transactionDto);
    }
    public void deleteTransaction(Long transactionId){
        transactionClient.deleteTransaction(transactionId);
    }

    public void updateTransaction(TransactionDto transactionDto){
        transactionClient.updateTransaction(transactionDto);
    }
}
