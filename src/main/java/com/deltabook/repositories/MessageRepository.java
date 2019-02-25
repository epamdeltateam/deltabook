package com.deltabook.repositories;

import com.deltabook.model.Message;
import com.deltabook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRecipientIDAndSenderIDOrderByCreatedAt(User senderId, User recipientId);

    List<Message> findByRecipientIDOrderByCreatedAt(User recipientId);

    Message findFirstByRecipientIDAndIsReadFalseOrderByCreatedAtDesc(User recipintId);

    String findMessagesBySenderIDAndRecipientID_query = "SELECT * FROM MESSAGES WHERE SENDER_ID = :senderId AND RECIPIENT_ID = :recipientId UNION SELECT * FROM MESSAGES WHERE RECIPIENT_ID = :senderId AND SENDER_ID = :recipientId ORDER BY CREATED_AT";
    @Query(value = findMessagesBySenderIDAndRecipientID_query, nativeQuery = true)
    List<Message> findMessagesBetweenTwoUsers(@Param("senderId") User senderId, @Param("recipientId") User recipientId);

    List <Message> findByRecipientID(User senderId);

    List<Message> findByRecipientIDOrSenderIDOrderByCreatedAt(User senderId, User recipientId);
}
