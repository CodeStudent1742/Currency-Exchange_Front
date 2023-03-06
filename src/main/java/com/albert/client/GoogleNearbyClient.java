package com.albert.client;

import com.albert.domain.dto.GoogleNearbyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleNearbyClient {

    private final RestTemplate restTemplate;
    private static final String NEARBY_URL = "http://localhost:8888/v1/google/cantor/nearby/KRK/mainSquare";
    private static GoogleNearbyClient googleNearbyClient;

    public static GoogleNearbyClient getInstance() {
        if (googleNearbyClient == null) {
            googleNearbyClient = new GoogleNearbyClient(new RestTemplate());
        }
        return googleNearbyClient;
    }

    public List<GoogleNearbyDto> getCantorsNearbyKRKMainSquare() {
        try {
            GoogleNearbyDto[] nearbyCantors= restTemplate.getForObject(NEARBY_URL, GoogleNearbyDto[].class);
            return Arrays.asList(Objects.requireNonNull(nearbyCantors));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
