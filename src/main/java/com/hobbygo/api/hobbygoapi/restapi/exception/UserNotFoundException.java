package com.hobbygo.api.hobbygoapi.restapi.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userName) {

        super(
                String.format("Usuario %s no encontrado", userName)
        );

    }
}
