package com.example.demo.model;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Role;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class AppUserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;

    public AppUserDto(AppUser user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
