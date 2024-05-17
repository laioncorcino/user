package com.corcino.users.domain.services;

import com.corcino.users.domain.dto.AuthenticateUserRequest;
import com.corcino.users.domain.dto.AuthenticateUserResponse;
import com.corcino.users.domain.dto.CreateUserRequest;
import com.corcino.users.domain.dto.CreateUserResponse;
import com.corcino.users.domain.entities.Role;
import com.corcino.users.domain.entities.User;
import com.corcino.users.domain.interfaces.UserDomainService;
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

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper mapper;
    private SHA256Component sha256Component;

    @Autowired
    public UserDomainServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper, SHA256Component sha256Component) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.sha256Component = sha256Component;
    }

    @Override
    public CreateUserResponse create(CreateUserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        Role role = roleRepository.findByName("DEFAULT");

        user.setUserId(UUID.randomUUID());
        user.setPass(sha256Component.hash(userRequest.getPass()));
        user.setRole(role);

        userRepository.save(user);

        CreateUserResponse response = mapper.map(user, CreateUserResponse.class);
        response.setRole(user.getRole().getName());
        response.setCreatedAt(Instant.now());

        return response;
    }

    @Override
    public AuthenticateUserResponse authenticate(AuthenticateUserRequest authRequest) {
        return null;
    }

}
