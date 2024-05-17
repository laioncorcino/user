package com.corcino.users.domain.interfaces;

import com.corcino.users.domain.dto.AuthenticateUserRequest;
import com.corcino.users.domain.dto.AuthenticateUserResponse;
import com.corcino.users.domain.dto.CreateUserRequest;
import com.corcino.users.domain.dto.CreateUserResponse;

public interface UserDomainService {

    AuthenticateUserResponse authenticate(AuthenticateUserRequest authRequest);
    CreateUserResponse create(CreateUserRequest userRequest);

}
