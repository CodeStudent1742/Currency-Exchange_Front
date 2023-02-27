package com.albert.service;

import com.albert.client.UserClient;
import com.albert.domain.dto.NewUserDto;
import com.albert.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService(UserClient.getInstance());
        }
        return userService;
    }

    public List<UserDto> getUsers() {
        return userClient.getAllUsers();
    }

    public void save(NewUserDto newUserDto) {
        userClient.saveNewUser(newUserDto);;
    }

    public void delete(String userName) {
        this.userClient.deleteUser(userName);
    }
}
