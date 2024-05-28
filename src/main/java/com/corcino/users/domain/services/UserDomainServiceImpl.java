package com.corcino.users.domain.services;

import com.corcino.users.domain.dto.AuthenticateUserRequest;
import com.corcino.users.domain.dto.AuthenticateUserResponse;
import com.corcino.users.domain.dto.CreateUserRequest;
import com.corcino.users.domain.dto.CreateUserResponse;
import com.corcino.users.domain.entities.Role;
import com.corcino.users.domain.entities.User;
import com.corcino.users.domain.exception.AccessDeniedException;
import com.corcino.users.domain.exception.EmailAlreadyRegisteredException;
import com.corcino.users.domain.interfaces.UserDomainService;
import com.corcino.users.infrastructure.component.JWTTokenComponent;
import com.corcino.users.infrastructure.component.RabbitMQProducerComponent;
import com.corcino.users.infrastructure.component.SHA256Component;
import com.corcino.users.infrastructure.repository.RoleRepository;
import com.corcino.users.infrastructure.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final SHA256Component sha256Component;
    private final RabbitMQProducerComponent rabbitProducer;
    private final JWTTokenComponent jwtTokenComponent;

    @Autowired
    public UserDomainServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            ModelMapper mapper,
            SHA256Component sha256Component,
            RabbitMQProducerComponent rabbitProducer,
            JWTTokenComponent jwtTokenComponent
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.sha256Component = sha256Component;
        this.rabbitProducer = rabbitProducer;
        this.jwtTokenComponent = jwtTokenComponent;
    }

    @Override
    public CreateUserResponse create(CreateUserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = mapper.map(userRequest, User.class);
        Role role = roleRepository.findByName("DEFAULT");

        user.setUserId(UUID.randomUUID());
        user.setPass(sha256Component.hash(userRequest.getPass()));
        user.setRole(role);

        userRepository.save(user);

        try {
            rabbitProducer.sendMessage(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CreateUserResponse response = mapper.map(user, CreateUserResponse.class);
        response.setRole(user.getRole().getName());
        response.setCreatedAt(Instant.now());

        return response;
    }

    @Override
    public AuthenticateUserResponse authenticate(AuthenticateUserRequest authRequest) {
        User user = userRepository.findByEmailAndPass(authRequest.getEmail(), sha256Component.hash(authRequest.getPass()));
        if (user == null) {
            throw new AccessDeniedException();
        }

        AuthenticateUserResponse response = new AuthenticateUserResponse();
        response.setUserId(user.getUserId());
        response.setRole(user.getRole().getName());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAccessToken(jwtTokenComponent.generateToken(user));
        response.setExpiration(jwtTokenComponent.getExpirationDate().toInstant());

        return response;
    }

}
