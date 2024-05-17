package com.corcino.users.infrastructure.repository;

import com.corcino.users.domain.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends MongoRepository<Role, UUID> {

    @Query("{'name':  ?0}")
    Role findByName(String name);

}
