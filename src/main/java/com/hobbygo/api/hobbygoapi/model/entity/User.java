package com.hobbygo.api.hobbygoapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hobbygo.api.hobbygoapi.model.validation.interfaces.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Document(collection = "users")
public class User {

    @Id
    @JsonIgnore
    private String id;

    @NotNull
    @JsonDeserialize
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    private String userName;

    @NotNull
    private String fullName;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private String playerId;

    private boolean enabled;

    private String roles[];

    private LocalDateTime joinDate;

    private Locale locale;

    public User(String email, String fullName, String userName, String password, String[] roles, Locale locale){

        setEmail(email);
        setFullName(fullName);
        setUserName(userName);
        setPassword(password);

        setRoles(roles);
        setJoinDate(LocalDateTime.now());
        setLocale(locale);
        setEnabled(false);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    private void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public Locale getLocale() {
        return locale;
    }

    private void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}