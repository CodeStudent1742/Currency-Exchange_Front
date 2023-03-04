package com.albert.client;

import com.albert.domain.dto.ExchangeOrderDto;
import com.albert.domain.dto.NewUserDto;
import com.albert.domain.dto.TransactionDto;
import com.albert.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    private static final String USER_URL = "http://localhost:8888/v1/user";
    private static UserClient userClient;

    public static UserClient getInstance() {
        if (userClient == null) {
            userClient = new UserClient(new RestTemplate());
        }
        return userClient;
    }

    public List<UserDto> getAllUsers() {
        try {
            UserDto[] users = restTemplate.getForObject(USER_URL, UserDto[].class);
            return Arrays.asList(Objects.requireNonNull(users));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }


    }
    public void saveNewUser(NewUserDto newUserDto) {
        try {
            restTemplate.postForObject(USER_URL, newUserDto, Void.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void deleteUser(String thisUserName) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(USER_URL)
                    .queryParam("userName", thisUserName)
                    .build().toUri();
            restTemplate.delete(uri);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
    public void updateUser(UserDto userDto){
        try{
            restTemplate.put(USER_URL,userDto, Void.class);
        }catch(RestClientException e){
            log.error(e.getMessage(), e);
        }
    }
    public List<ExchangeOrderDto> getUserExchangeOrders(Long userId) {
            URI uri = UriComponentsBuilder.fromUriString(USER_URL + "/" + userId + "/exchangeOrders"  )
                    .build().toUri();
        try {
            ExchangeOrderDto[] exchangeOrdersDto = restTemplate.getForObject(uri, ExchangeOrderDto[].class);
            return Arrays.asList(Objects.requireNonNull(exchangeOrdersDto));
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

}
