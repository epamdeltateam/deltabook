package com.deltabook.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private enum role {ROLE_ADMIN, ROLE_USER};
    private String password;
    private String firstName;
    private String lastName;
    private String picture;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Contact> contact;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Message> message;
}