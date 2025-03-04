package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {


        @NotNull(message = "First name is required")
        @NotBlank(message = "First name cannot be blank")
        private String firstName;

        @NotNull(message = "Last name is required")
        @NotBlank(message = "Last name cannot be blank")
        private String lastName;

        @Email(message = "Invalid email format")
        @NotNull(message = "Email is required")
        private String email;

        @NotNull(message = "Password is required")
        @NotBlank(message = "Password cannot be blank")
        private String pass;
    }


