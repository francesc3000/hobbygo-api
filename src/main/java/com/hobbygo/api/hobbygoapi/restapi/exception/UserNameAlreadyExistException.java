package com.hobbygo.api.hobbygoapi.restapi.exception;

public class UserNameAlreadyExistException extends RuntimeException {

    public UserNameAlreadyExistException(String userName) {

        super(
                String.format("Username %s ya existe.", userName)
        );

    }
}
