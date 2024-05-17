package com.corcino.users.infrastructure.repository;

import com.corcino.users.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    @Query("{'email':  ?0}")
    User findByEmail(String email);

    @Query("{'email':  ?0, 'password':  ?1}")
    User findByEmailAndPass(String email, String pass);

}
