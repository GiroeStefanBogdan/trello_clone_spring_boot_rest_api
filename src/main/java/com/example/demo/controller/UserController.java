package com.example.demo.controller;

import com.example.demo.model.CreateUserDto;
import com.example.demo.model.UpdateUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/api/v1/user";
    public static final String USER_PATH_ID = "/api/v1/user/{id}";
//    private final PasswordEncoder passwordEncoder;  // ✅ Injected PasswordEncoder

    private final AppUserService userService;

    @GetMapping(USER_PATH)
    public ResponseEntity<List<UserDto>> listUsers() {
        List<UserDto> users = userService.listAllUsers();

        return ResponseEntity.ok(users);
    }

    //TODO: implement get user by id


    @PostMapping(USER_PATH + "/login")
    public ResponseEntity<CreateUserDto> createUser(@Validated @RequestBody CreateUserDto createUserDto) {
        CreateUserDto savedUser = userService.saveNewUser(createUserDto);

        return  new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping(USER_PATH_ID)
    public ResponseEntity<Optional<UpdateUserDto>> updateUserDto(@PathVariable("id") UUID id, @Validated @RequestBody UpdateUserDto updateUserDto) {

        Optional<UpdateUserDto> updatedUserDto = userService.updateUserById(id, updateUserDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //TODO: implement patch operation



    //TODO: implement delete operation
    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        if(! userService.deleteUserById(id)){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };

        return ResponseEntity.ok().build();
    }
}
