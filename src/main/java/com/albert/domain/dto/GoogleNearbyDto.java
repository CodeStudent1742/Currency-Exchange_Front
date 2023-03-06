package com.albert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleNearbyDto {
    private String name;
    private Double rating;
    private Double lat;
    private Double lng;
    private String vicinity;
}
