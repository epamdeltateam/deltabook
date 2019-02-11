package com.deltabook.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private Users sender_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private Users recipient_id;
    private String Body;
    private Date created_at;
}
