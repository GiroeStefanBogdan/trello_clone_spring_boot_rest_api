package com.example.demo.mappers;

import com.example.demo.entities.AppUser;
import com.example.demo.model.CreateUserDto;
import com.example.demo.model.UpdateUserDto;
import com.example.demo.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(AppUser user);
    AppUser userDtoToUser(UserDto userDto);

    AppUser createUserDtoToUser(CreateUserDto createUserDto);
    CreateUserDto userToCreateUserDto(AppUser user);

    AppUser updateUserDtoToUser(UpdateUserDto updateUserDto);
    UpdateUserDto userToUpdateUserDto(AppUser user);

//    AppUserDto  appUserToAppUserDto(AppUser appUser);
//    AppUser appUserDtoToAppUser(AppUserDto appUserDto);
}
