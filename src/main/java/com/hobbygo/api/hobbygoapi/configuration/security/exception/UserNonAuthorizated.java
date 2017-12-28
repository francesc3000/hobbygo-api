package com.hobbygo.api.hobbygoapi.configuration.security.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({ "status", "message","links" })
public class UserNonAuthorizated {
    private int status;
    private String message;
    private List<Map<String,Object>> links;

    public UserNonAuthorizated(int status, String message){
        setStatus(status);
        setMessage(message);
        setLinks(new ArrayList<>());
        getLinks().add(new HashMap<>());
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, Object>> getLinks() {
        return links;
    }

    public void setLinks(List<Map<String, Object>> links) {
        this.links = links;
    }

    public void addLink(String label, String value){
        getLinks().get(0).put(label,value);
    }
}
