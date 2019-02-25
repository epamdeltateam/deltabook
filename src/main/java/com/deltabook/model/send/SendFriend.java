package com.deltabook.model.send;

import com.deltabook.model.User;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class SendFriend {

    private String login;
    private String firstName;
    private String lastName;
    private String picture;

    public SendFriend(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        if (user.getPicture() != null) {
            this.picture = Base64.getEncoder().encodeToString(user.getPicture());
        } else {
            try {
                File file = ResourceUtils.getFile("classpath:static/images/default-avatar.jpg");
                byte[] fileContent = FileUtils.readFileToByteArray(file);
                this.picture = Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException ex) {
                this.picture = null;
            }
        }
        System.out.println(this.picture);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
