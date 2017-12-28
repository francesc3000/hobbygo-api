package com.hobbygo.api.hobbygoapi.restapi.dto;

public class ContactDto {

    private String name;
    private String email;
    private String phone;
    private Boolean send;

    public  ContactDto(){}

    public ContactDto(String name, String email, String phone, Boolean send){
        setName(name);
        setEmail(email);
        setPhone(phone);
        setSend(send);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }
}
