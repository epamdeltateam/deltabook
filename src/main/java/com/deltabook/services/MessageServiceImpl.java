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
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(User userFrom, SendMessage sendMessage) {
        User userTo = userRepository.findUserByLogin(sendMessage.getNickName());
        if(Objects.isNull(userTo)) return null;
        String messageBody = sendMessage.getBody();
        return messageRepository.save(new Message(userFrom, userTo, messageBody));
    }

    @Override
    public Message getLastUnreadMessage(User recipientId) {
        return messageRepository.findFirstByRecipientIDAndIsReadFalseOrderByCreatedAtDesc(recipientId);
    }

    @Override
    public List<Message> getDialog(User recipientId, User senderId) {
        return messageRepository.findMessagesBetweenTwoUsers(senderId, recipientId);
    }

    @Override
    public List<User> getAllChatCompanionsOfUser(User user) {
        Set<User> setOfUsers = new HashSet<>();
        List<Message> messageList = messageRepository.findByRecipientIDOrSenderIDOrderByCreatedAt(user, user);
        for (Message msg : messageList) {
            if (msg.getSenderID().equals(user)) {
                setOfUsers.add(msg.getRecipientID());
            }
            else {
                setOfUsers.add(msg.getSenderID());
            }
        }

        return new ArrayList<>(setOfUsers);
    }

    @Override
    public List<Message> generatedDialogBetweenUsers(User userRecipient, User userSender, String principalLogin) {
        return getDialog(userRecipient,userSender );
    }
    public List<Message>  UpdatedDialogBetweenUsers(String recipient, String sender, Authentication authentication, Model model) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        User userRecipient = userRepository.findUserByLogin(recipient);
        User userSender = userRepository.findUserByLogin(sender);
        return getDialog(userRecipient,userSender );
    }

    public void UpdateMessage(Message message) {
        messageRepository.saveAndFlush(message);
    }

}
