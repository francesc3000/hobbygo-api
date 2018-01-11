package com.hobbygo.api.hobbygoapi.restapi.exception;

public class EmailNoExistException extends RuntimeException {

    public EmailNoExistException(String email) {

        super(
                String.format("Correo electrónico %s no existe.", email)
        );

    }
}
