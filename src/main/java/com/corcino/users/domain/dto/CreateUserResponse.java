package com.corcino.users.domain.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateUserResponse {

    private UUID userId;
    private String name;
    private String email;
    private String role;
    private Instant createdAt;

}
