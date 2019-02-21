package com.deltabook.services;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import com.deltabook.model.send.SendMessage;
import com.deltabook.repositories.MessageRepository;
import com.deltabook.repositories.UserRepository;
import com.deltabook.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    public Message sendMessage(User userFrom, SendMessage sendMessage) {
        User userTo = userRepository.findUserByLogin(sendMessage.getNickName());
        String messageBody = sendMessage.getBody();
        Message message = messageRepository.save(new Message(userFrom, userTo, messageBody));
        return message;
    }

    @Override
    public Message getLastUnreadMessage(User recipientId) {
        return messageRepository.findFirstByRecipientIDAndIsReadFalseOrderByCreatedAtDesc(recipientId);
    }

    @Override
    public List<Message> getDialog(User recipientId, User senderId) {
        List<Message> messageList = messageRepository.findMessagesBetweenTwoUsers(senderId, recipientId);
        return messageList;
    }

    @Override
    public List<User> getAllChatCompanionsOfUser(User user) {
        Set<User> setOfUsers = new HashSet<>();
        List<Message> messageList = messageRepository.findByRecipientIDOrSenderIDOrderByCreatedAt(user, user);
        for (Message msg : messageList) {
            if (msg.getSenderID().equals(user))
                setOfUsers.add(msg.getRecipientID());
            else setOfUsers.add(msg.getSenderID());
        }

        return new ArrayList<>(setOfUsers);
    }

    @Override
    public Model generatedDialogBetweenUsers(String recipient, String sender, Authentication authentication, Model model) {
        List<Message> messageList = new ArrayList<Message>();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = userRepository.findUserByLogin(recipient);
        User userSender = userRepository.findUserByLogin(sender);
        messageList = getDialog(userRecipient,userSender );
        String recipientLogin = userRecipient.getLogin();
        String senderLogin = userSender.getLogin();
        if(recipientLogin != principal.getUser().getLogin()) {
            if(senderLogin == principal.getUser().getLogin()) {
                String temp = recipientLogin;
                recipientLogin = senderLogin;
                senderLogin = temp;

            }
        }
        model.addAttribute("messageList", messageList);
        model.addAttribute("recipientLogin", recipientLogin);
        model.addAttribute("senderLogin", senderLogin);
        String recipientPic = "", senderPic = " ";
        if (userRecipient.getPicture() != null){
            recipientPic = Base64.getEncoder().encodeToString(userRecipient.getPicture());
        }
        if (userSender.getPicture() != null){
            senderPic = Base64.getEncoder().encodeToString(userSender.getPicture());
        }
        model.addAttribute("recipientPic", recipientPic);
        model.addAttribute("senderPic", senderPic);
        model.addAttribute("sendMessage", new SendMessage());
        return model;
    }
    public List<Message>  UpdatedDialogBetweenUsers(String recipient, String sender, Authentication authentication, Model model) {
        List<Message> messageList = new ArrayList<Message>();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = userRepository.findUserByLogin(recipient);
        User userSender = userRepository.findUserByLogin(sender);
        messageList = getDialog(userRecipient,userSender );
        return messageList;
    }

}
