package com.deltabook.services;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import com.deltabook.model.send.SendMessage;
import com.deltabook.repositories.MessageRepository;
import com.deltabook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
