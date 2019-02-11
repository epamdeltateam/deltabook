package com.deltabook.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "senderID_id")
    private User senderID;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipientID_id")
    private User recipientID;
    private String body;
    private Date createdAt;
}
