package com.corcino.users.application.controller;

import com.corcino.users.domain.dto.AuthenticateUserRequest;
import com.corcino.users.domain.dto.AuthenticateUserResponse;
import com.corcino.users.domain.dto.CreateUserRequest;
import com.corcino.users.domain.dto.CreateUserResponse;
import com.corcino.users.domain.interfaces.UserDomainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDomainService userDomainService;

    @Autowired
    public UserController(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest userRequest) {
        CreateUserResponse createUserResponse = userDomainService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticateUserResponse> authenticate(@RequestBody @Valid AuthenticateUserRequest authenticateUserRequest) {
        AuthenticateUserResponse authenticateUserResponse = userDomainService.authenticate(authenticateUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticateUserResponse);
    }

}
