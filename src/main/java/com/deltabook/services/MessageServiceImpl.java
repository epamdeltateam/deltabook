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

    public int messageListSize = 0;

    public Message sendMessage(User userFrom, SendMessage sendMessage) {
        User userTo = userRepository.findUserByLogin(sendMessage.getNickanme());
        String messageBody = sendMessage.getBody();
        Message message = messageRepository.save(new Message(userFrom, userTo, messageBody));
        return message;
    }

    @Override
    public Message findByRecipientIDOrderByCreatedAt(User recipientId) {
        List<Message> messageList;
        messageList = messageRepository.findByRecipientIDOrderByCreatedAt(recipientId);
        //System.out.println(messageList.size());
        if (messageListSize == messageList.size() && messageListSize == 0 ) {
            System.out.println("Empty");
            return null;
        } else {
            messageListSize = messageList.size();
            // System.out.println(messageListSize);
            if (messageListSize == 0) return null;
            Message message;
            if (messageList.size() != 0)
                message = messageList.get(messageListSize - 1);
            else
                message = messageList.get(messageListSize);

            return message;

        }

    }
}
