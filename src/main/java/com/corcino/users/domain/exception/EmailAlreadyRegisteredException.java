package com.corcino.users.domain.exception;

import java.io.Serial;

public class EmailAlreadyRegisteredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyRegisteredException() {
        super("Email ja cadastrado");
    }
}
