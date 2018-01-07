package com.hobbygo.api.hobbygoapi.restapi.exception;

public class UserNotValidatedException extends RuntimeException {

    public UserNotValidatedException(String userName) {

        super(
                String.format("Usuario %s no validado, revisar correo electronico", userName)
        );

    }
}
