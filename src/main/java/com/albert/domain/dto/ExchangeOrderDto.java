package com.albert.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrderDto {

    private Long orderId;

    private LocalDate exchangeDate;
    private ExchangeStatus exchangeStatus;
    private Long userId;

    private List<Long> orderTransactionIds;
}
