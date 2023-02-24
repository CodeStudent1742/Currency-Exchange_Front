package com.albert.service;

import com.albert.domain.dto.NewUserDto;
import com.albert.domain.dto.UserDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService {

    private Set<UserDto> users;
    private static UserService userService;

    private UserService(){
        this.users = exampleData();
        //procedura wczytania z bazy danych
    }
    public static UserService getInstance(){
        if(userService == null){
            userService = new UserService();
        }
        return userService;
    }
    public Set<UserDto> getUsers() {
        return new HashSet<>(users);
    }

    public void addUser(NewUserDto newUserDto) {
        // Procedura zapisująca !!!
        //1. Wysłanie NewUserDto
        //2.Zapis do bazy
        //3. Odbior UserDto
        //4. Dodanie do HashSet
        //*
//        this.books.add(book);
    }
    private Set<UserDto> exampleData() {
        Set<UserDto> users = new HashSet<>();
        users.add(new UserDto(1L,1L,1L, List.of(),"ExampleUser1"));
        users.add(new UserDto(2L,2L,2L, List.of(),"ExampleUser2"));
        users.add(new UserDto(3L,3L,3L, List.of(),"ExampleUser3"));
        users.add(new UserDto(4L,4L,4L, List.of(),"ExampleUser4"));
        return users;
    }
    public void save(UserDto userDto) {
        this.users.add(userDto);
    }

    public void delete(UserDto userDto) {
        this.users.remove(userDto);
    }
}
