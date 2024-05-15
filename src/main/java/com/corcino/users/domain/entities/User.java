package com.corcino.users.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class User {

    private UUID userId;
    private String name;
    private String email;
    private String pass;

}
