package com.deltabook.model.send;

public class SendChangeUser {
    private String NickName;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getNewLastName() {
        return NewLastName;
    }

    public void setNewLastName(String newLastName) {
        NewLastName = newLastName;
    }

    private String NewLastName;
}
