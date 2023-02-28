package com.albert.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CantorClient {

    private final RestTemplate restTemplate;

    private static final String CANTOR_ASK_URL = "http://localhost:8888/v1/cantor/ask";
    private static final String CANTOR_BID_URL = "http://localhost:8888/v1/cantor/bid";
    private static CantorClient cantorClient;

    public static CantorClient getInstance() {
        if (cantorClient == null) {
            cantorClient = new CantorClient(new RestTemplate());
        }
        return cantorClient;
    }





}
