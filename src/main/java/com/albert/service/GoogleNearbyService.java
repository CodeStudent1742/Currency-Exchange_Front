package com.albert.service;

import com.albert.client.GoogleNearbyClient;
import com.albert.domain.dto.GoogleNearbyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleNearbyService {

    private final GoogleNearbyClient googleNearbyClient;
    private static GoogleNearbyService googleNearbyService;

    public static GoogleNearbyService getInstance() {
        if (googleNearbyService == null) {
            googleNearbyService = new GoogleNearbyService(GoogleNearbyClient.getInstance());
        }
        return googleNearbyService;
    }
    public List<GoogleNearbyDto> getCantorsNearbyKRKMainSquare(){
       return googleNearbyClient.getCantorsNearbyKRKMainSquare();
    }
}
