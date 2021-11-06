package com.deltabook.model.send;

import com.deltabook.model.Contact;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;

public class SendFriendRequest {
    private String requestMessage;
    private String friendNickname;

    private Timestamp timestamp;
    private Long id;
    private String picture;

    public SendFriendRequest() {
    }

    public SendFriendRequest(Contact contact) {
        this.id = contact.getId();
        this.requestMessage = contact.getRequestMessage();
        this.timestamp = contact.getCreatedAt();
        this.friendNickname = contact.getFriendFromId().getLogin();
        if (contact.getFriendFromId().getPicture() != null) {
            this.picture = Base64.getEncoder().encodeToString(contact.getFriendFromId().getPicture());
        } else {
            try {
                File file = ResourceUtils.getFile("classpath:static/images/default-avatar.jpg");
                byte[] fileContent = FileUtils.readFileToByteArray(file);
                this.picture = Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException ex) {
                this.picture = null;
            }
        }
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}