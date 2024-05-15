package com.corcino.users.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Role {

    private UUID roleId;
    private String name;

}
