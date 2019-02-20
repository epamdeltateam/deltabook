package com.deltabook.services;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import com.deltabook.model.send.SendMessage;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface MessageService {

    Message sendMessage(User userFrom, SendMessage sendMessage);

    Message getLastUnreadMessage(User recipientId);

    List<Message> getDialog(User recipientId, User senderId);

    List <User> getAllChatCompanionsOfUser(User user);

    Model generatedDialogBetweenUsers(String recipient, String sender, Authentication authentication, Model model);

}
