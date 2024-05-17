package com.corcino.users.application.controller;

import com.corcino.users.domain.dto.CreateUserRequest;
import com.corcino.users.domain.dto.CreateUserResponse;
import com.corcino.users.domain.interfaces.UserDomainService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDomainService userDomainService;

    @Autowired
    public UserController(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @PostMapping("/auth")
    public Void authenticate() {
        throw new NotImplementedException();
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest userRequest) {
        CreateUserResponse createUserResponse = userDomainService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

}
