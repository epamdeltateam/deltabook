package com.deltabook.demo.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String role;
    private String password;
    private String first_name;
    private String last_name;
    private String picture;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Contacts> contacts;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Messages> messages;
}