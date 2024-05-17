package com.corcino.users.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class User {

    @Id
    private UUID userId;
    private String name;

    @Indexed(unique = true)
    private String email;
    private String pass;

    @DBRef
    private Role role;

}
