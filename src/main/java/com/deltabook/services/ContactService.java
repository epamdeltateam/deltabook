package com.deltabook.services;

import com.deltabook.model.Contact;
import com.deltabook.model.User;

import java.util.List;

public interface ContactService {
    String sendRequestFriend(User fromUser, User toUser, String requestMessage);
    List<Contact> getAllRequestsFromUser(User user);
    List<Contact> getAllRequestsToUser(User user);
    List<Contact> getAllFriends(User user);
    void confirmRequest(User fromUser, User toUser);
    void declineRequest(User fromUser, User toUser);
    Contact getLastNotAcceptedRequest(User friendTo);
}
