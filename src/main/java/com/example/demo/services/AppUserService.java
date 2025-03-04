package com.example.demo.services;


import com.example.demo.entities.AppUser;
import com.example.demo.model.CreateUserDto;
import com.example.demo.model.UpdateUserDto;
import com.example.demo.model.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    List<UserDto> listAllUsers();
    CreateUserDto saveNewUser(CreateUserDto createUserDto);

    Optional<UpdateUserDto> updateUserById(UUID id, UpdateUserDto updateUserDto);

    Boolean deleteUserById(UUID id);

    AppUser findByEmail(String email);
}
