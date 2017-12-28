package com.hobbygo.api.hobbygoapi.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customSequences")
public class CustomSequences {

    @Id
    private String id;
    private String seq;

    public String getId() {
        return id;
    }

    public String getSeq() {
        return seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
