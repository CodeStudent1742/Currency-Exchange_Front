package com.albert.client;

import com.albert.domain.Cantor;
import com.albert.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CantorClient {

    private final RestTemplate restTemplate;

//    private static final String CANTOR_ASK_URL = "http://localhost:8888/v1/cantor/ask";
//    private static final String CANTOR_BID_URL = "http://localhost:8888/v1/cantor/bid";
    private static final String CANTOR_URL = "http://localhost:8888/v1/cantor/all";
    private static CantorClient cantorClient;

    public static CantorClient getInstance() {
        if (cantorClient == null) {
            cantorClient = new CantorClient(new RestTemplate());
        }
        return cantorClient;
    }

    public Cantor getCantor(){
        URI uriA = UriComponentsBuilder.fromUriString(CANTOR_URL)
                .build().toUri();
        try {
            Cantor cantor = restTemplate.getForObject(uriA, Cantor.class);
            return (Objects.requireNonNull(cantor));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
