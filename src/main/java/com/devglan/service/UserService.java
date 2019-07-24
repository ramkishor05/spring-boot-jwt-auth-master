package com.devglan.service;

import java.util.List;

import com.devglan.dto.UserDto;
import com.devglan.entities.User;

public interface UserService {

    User save(UserDto user);
    
    List<User> findAll();
    
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
