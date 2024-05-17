package com.corcino.users.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Role {

    @Id
    private UUID roleId;

    @Indexed(unique = true)
    private String name;

}
