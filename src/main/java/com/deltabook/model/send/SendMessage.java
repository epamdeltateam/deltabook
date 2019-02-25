package com.deltabook.model.send;

import com.deltabook.model.Message;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;

public class SendMessage {

    public SendMessage() {
    }

    public SendMessage(Message message){
        this.id = message.getId();
        this.nickName =message.getSenderID().getLogin();
        this.body = message.getBody();
        this.timestamp =message.getCreatedAt();
        if (message.getSenderID().getPicture() != null) {
            this.picture = Base64.getEncoder().encodeToString(message.getSenderID().getPicture());
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

    private String picture;

    private Long id;

    private String nickName;

    private String body;

    private Timestamp timestamp;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}