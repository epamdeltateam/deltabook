package com.deltabook.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_from_id")
    private Users friend_from_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_to_id")
    private Users friend_to_id;
    private boolean isAccepted;
    private String requestMessage;
}
