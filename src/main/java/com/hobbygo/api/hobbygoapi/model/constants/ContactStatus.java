package com.hobbygo.api.hobbygoapi.model.constants;

public enum ContactStatus {
    CONNECTED("connected"),
    SEND("send"),
    SENDED("sended");

    private String status;

    ContactStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
