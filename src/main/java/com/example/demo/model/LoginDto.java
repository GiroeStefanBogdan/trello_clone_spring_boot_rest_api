package com.example.demo.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String pass;
}
