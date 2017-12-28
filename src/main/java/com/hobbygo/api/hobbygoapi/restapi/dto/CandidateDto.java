package com.hobbygo.api.hobbygoapi.restapi.dto;

public class CandidateDto {

    private String email;

    public CandidateDto(){}

    public CandidateDto(String email){
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
