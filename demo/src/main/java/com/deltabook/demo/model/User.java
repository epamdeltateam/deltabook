package com.deltabook.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private Roles role;
    private String password;
    private String firstName;
    private String lastName;
    private String picture;

    @OneToMany(mappedBy = "friendFromId", cascade = CascadeType.ALL)
    private List<Contact> contacts_from;

    @OneToMany(mappedBy = "friendToId", cascade = CascadeType.ALL)
    private List<Contact> contacts_to;

    @OneToMany(mappedBy = "senderID", cascade = CascadeType.ALL)
    private List<Message> messages_sender;

    @OneToMany(mappedBy = "recipientID", cascade = CascadeType.ALL)
    private List<Message> messages_recipient;

}