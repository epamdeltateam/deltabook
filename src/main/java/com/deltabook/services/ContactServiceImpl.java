package com.deltabook.services;

import com.deltabook.model.Contact;
import com.deltabook.model.User;
import com.deltabook.model.send.SendFriend;
import com.deltabook.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public String sendRequestFriend(User fromUser, User toUser, String requestMessage) {
        Contact newContact = new Contact(fromUser, toUser, requestMessage);
        if (Objects.nonNull(contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser))) {
            return "Request was sent before";
        }
        contactRepository.save(newContact);
        return "Success";
    }

    @Override
    public List<Contact> getAllRequestsFromUser(User user) {
        return contactRepository.findByIsAcceptedAndFriendFromId(false, user);
    }

    @Override
    public List<Contact> getAllRequestsToUser(User user) {
        return contactRepository.findByIsAcceptedAndFriendToId(false, user);
    }

    @Override
    public List<SendFriend> getAllFriends(User user) {
        List<SendFriend> friends = new ArrayList<>();
        List<Contact> requestTo = contactRepository.findByIsAcceptedAndFriendFromId(true, user);
        for (Contact contact : requestTo) {
            friends.add(new SendFriend(contact.getFriendToId()));
        }
        List<Contact> requestFrom = contactRepository.findByIsAcceptedAndFriendToId(true, user);
        for (Contact contact : requestFrom) {
            friends.add(new SendFriend(contact.getFriendFromId()));
        }
        return friends;
    }

    @Override
    public void confirmRequest(User fromUser, User toUser) {
        Contact contact = contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser);
        contact.setAccepted(true);
        contactRepository.saveAndFlush(contact);
    }

    @Override
    public void declineRequest(User fromUser, User toUser) {
        Contact contact = contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser);
        contactRepository.delete(contact);
    }

    @Override
    public Contact getLastNotAcceptedRequest(User friendTo) {
        return contactRepository.findFirstByFriendToIdAndIsAcceptedFalseOrderByCreatedAtDesc(friendTo);
    }

    @Override
    public boolean checkIsContactExists(User fromUser, User toUser) {
        Contact contact = contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser);
        return contact != null;
    }
}
