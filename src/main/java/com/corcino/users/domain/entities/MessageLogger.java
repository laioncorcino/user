package com.corcino.users.domain.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document
public class MessageLogger {

    private UUID messageLoggerId;
    private String message;
    private String status;
    private Instant createdAt;

    @DBRef
    private User user;

}
