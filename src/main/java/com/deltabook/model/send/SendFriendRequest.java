package com.deltabook.model.send;

import com.deltabook.model.Contact;
import java.sql.Timestamp;

public class SendFriendRequest {
    private String requestMessage;
    private String friendNickname;

    private Timestamp timestamp;
    private Long id;

    public SendFriendRequest() {
    }

    public SendFriendRequest(Contact contact){
        this.id = contact.getId();
        this.requestMessage = contact.getRequestMessage();
        this.timestamp = contact.getCreatedAt();
        this.friendNickname =contact.getFriendFromId().getLogin();
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
