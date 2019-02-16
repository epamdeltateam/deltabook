package com.deltabook.repositories;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRecipientIDAndSenderIDOrderByCreatedAt(User senderId, User recipientId);

    List<Message> findByRecipientIDOrderByCreatedAt(User recipientId);

}
