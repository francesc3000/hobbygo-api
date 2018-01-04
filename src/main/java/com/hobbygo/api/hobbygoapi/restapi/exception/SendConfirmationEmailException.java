package com.hobbygo.api.hobbygoapi.restapi.exception;

public class SendConfirmationEmailException extends RuntimeException {

    public SendConfirmationEmailException() {

        super(
                String.format("No se pudo enviar el correo de confirmación")
        );

    }
}
