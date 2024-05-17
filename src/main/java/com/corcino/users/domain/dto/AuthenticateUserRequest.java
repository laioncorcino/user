package com.corcino.users.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticateUserRequest {

    @NotBlank(message = "email is required")
    @Email(message = "email must is valid")
    private String email;

    @NotBlank(message = "pass is required")
    @Size(min = 8, message = "pass must be min 8 caracter")
    private String pass;

}
