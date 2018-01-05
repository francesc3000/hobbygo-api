package com.hobbygo.api.hobbygoapi.restapi.dto;

import com.hobbygo.api.hobbygoapi.model.validation.interfaces.PasswordMatches;
import com.hobbygo.api.hobbygoapi.model.validation.interfaces.ValidEmail;
import com.hobbygo.api.hobbygoapi.model.validation.interfaces.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class CreateUserDto {

    @NotNull
    @Size(min = 1)
    @ValidEmail
    private String email;

    @NotNull
    @Size(min = 1)
    private String fullName;

    @NotNull
    @Size(min = 1)
    private String userName;

    @NotNull
    @Size(min = 1)
    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
