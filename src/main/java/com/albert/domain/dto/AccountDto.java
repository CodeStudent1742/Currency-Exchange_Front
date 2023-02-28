package com.albert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long accountId;

    private BigDecimal balancePLN = BigDecimal.valueOf(0);
    private BigDecimal balanceEUR= BigDecimal.valueOf(0);
    private BigDecimal balanceUSD= BigDecimal.valueOf(0);
    private BigDecimal balanceCHF= BigDecimal.valueOf(0);
    private BigDecimal balanceGBP= BigDecimal.valueOf(0);

    private Long userId;

}
