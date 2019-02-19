package com.deltabook.services;

import com.deltabook.model.Contact;
import com.deltabook.model.User;
import com.deltabook.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactRepository contactRepository;

    public String sendRequestFriend(User fromUser, User toUser, String requestMessage) {
        Contact newContact = new Contact(fromUser, toUser, requestMessage);
        if (contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser) != null) {
            return "Request was sent before";
        }
        contactRepository.save(new Contact(fromUser, toUser, requestMessage));
        return "Success";
    }

    public List<Contact> getAllRequestsFromUser(User user) {
        return contactRepository.findByIsAcceptedAndFriendFromId(false, user);
    }

    public List<Contact> getAllRequestsToUser(User user) {
        return contactRepository.findByIsAcceptedAndFriendToId(false,user);
    }

    public List<Contact> getFriendsTo(User user) {
        List<Contact> FriendsListTo = contactRepository.findByIsAcceptedAndFriendToId(true, user);
        return FriendsListTo;
    }

    public List<Contact> getFriendsFrom(User user) {
        List<Contact> FriendsListFrom = contactRepository.findByIsAcceptedAndFriendFromId(true, user);
        return FriendsListFrom;
    }


    public void confirmRequest(User fromUser, User toUser) {
        Contact contact = contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser);
        contact.setAccepted(true);
        contactRepository.saveAndFlush(contact);
    }

    public void declineRequest(User fromUser, User toUser){
        Contact contact = contactRepository.findByFriendFromIdAndFriendToId(fromUser, toUser);
        contactRepository.delete(contact);
    }

    @Override
    public Contact getLastNotAcceptedRequest(User friendTo) {
        return contactRepository.findFirstByFriendToIdAndIsAcceptedFalseOrderByCreatedAtDesc(friendTo);
    }

    @Override
    public void proceedFriendRequest(User fromUser, User toUser, String action) {
        if(action.equals("decline"))
            declineRequest(fromUser, toUser);
        if(action.equals("accept"))
            confirmRequest(fromUser, toUser);
        return;
    }
}
