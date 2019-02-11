package com.deltabook.demo.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_ID")
    private User senderID;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_ID")
    private User recipientID;
    private String body;
    private Timestamp createdAt;
}
