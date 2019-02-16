package com.deltabook.services;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import com.deltabook.model.send.SendMessage;

import java.util.List;

public interface MessageService {

    Message sendMessage(User userFrom, SendMessage sendMessage);

    Message findByRecipientIDOrderByCreatedAt(User recipientId);

}
