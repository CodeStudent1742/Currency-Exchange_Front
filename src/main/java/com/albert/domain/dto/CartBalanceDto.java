package com.albert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartBalanceDto {

    private Long cartBalanceId;

    private Double balancePLN = 0.0;
    private Double balanceEUR = 0.0;
    private Double balanceUSD = 0.0;
    private Double balanceCHF = 0.0;
    private Double balanceGBP = 0.0;

    private Long cartId;

}
