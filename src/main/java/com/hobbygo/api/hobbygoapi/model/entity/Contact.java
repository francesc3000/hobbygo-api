package com.hobbygo.api.hobbygoapi.model.entity;

import com.hobbygo.api.hobbygoapi.model.constants.ContactStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contacts")
public class Contact {

    @Id
    private String email;
    private String name;
    private String phone;
    private ContactStatus status;

    public Contact(String name, String email, String phone){
        setName(name);
        setEmail(email);
        setPhone(phone);
        setStatus(ContactStatus.CONNECTED);
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

    public Boolean isSended() {
        if(!getStatus().equals(ContactStatus.SENDED))
            return true;

        return false;
    }

    public ContactStatus getStatus() {
        return status;
    }

    private void setStatus(ContactStatus status) {
        this.status = status;
    }

    public void markAsSended() {
        setStatus(ContactStatus.SENDED);
    }

    public void mark2send(){
        setStatus(ContactStatus.SEND);
    }

    public Boolean isMarked2send(){
        if(getStatus().equals(ContactStatus.SEND))
            return true;

        return false;
    }
}
