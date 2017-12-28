package com.hobbygo.api.hobbygoapi.model.constants;

public enum FriendStatus {
    CONNECTED("connected"),
    CANDIDATE("candidate"),
    BLOCKED("blocked"),
    MUTUAL("markAsMutual");

    private String status;

    FriendStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
