package com.hobbygo.api.hobbygoapi.restapi.resource;

import com.hobbygo.api.hobbygoapi.model.constants.ContactStatus;
import com.hobbygo.api.hobbygoapi.model.entity.Contact;
import org.springframework.hateoas.ResourceSupport;

public class ContactResource extends ResourceSupport {

    private String email;
    private String name;
    private ContactStatus status;

    public ContactResource(Contact contact){
        setEmail(contact.getEmail());
        setName(contact.getName());
        setStatus(contact.getStatus());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactStatus getStatus() {
        return status;
    }

    public void setStatus(ContactStatus status) {
        this.status = status;
    }
}
