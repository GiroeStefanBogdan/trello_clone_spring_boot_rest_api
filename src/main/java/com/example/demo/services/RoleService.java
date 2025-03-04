package com.example.demo.services;

import com.example.demo.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);

}
