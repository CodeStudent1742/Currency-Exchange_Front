package com.albert.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cantor {

    private Long cantorRatesId;

    private LocalDate ratesCheckDate = LocalDate.now();

    private Double purchaseRateEUR;

    private Double sellingRateEUR;

    private Double purchaseRateUSD;

    private Double sellingRateUSD;

    private Double purchaseRateGBP;

    private Double sellingRateGBP;

    private Double purchaseRateCHF;

    private Double sellingRateCHF;

}

