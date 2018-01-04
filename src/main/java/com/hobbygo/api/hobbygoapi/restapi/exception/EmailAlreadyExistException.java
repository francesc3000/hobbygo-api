package com.hobbygo.api.hobbygoapi.restapi.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(String email) {

        super(
                String.format("Correo electrónico %s ya existe.", email)
        );

    }
}
