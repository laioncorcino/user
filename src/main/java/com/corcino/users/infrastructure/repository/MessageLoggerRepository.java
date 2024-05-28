package com.corcino.users.infrastructure.repository;

import com.corcino.users.domain.entities.MessageLogger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageLoggerRepository extends MongoRepository<MessageLogger, UUID> {
}
