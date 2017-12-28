package com.hobbygo.api.hobbygoapi.service.exception;

public class UpdatedUserNotFoundException extends Exception {

    String userName;

    public UpdatedUserNotFoundException(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}
