package com.deltabook.demo.model;

import javax.persistence.*;
import java.util.List;

import static com.deltabook.demo.model.Roles.ROLE_USER;

@Entity
@Table(name = "users")
public class User {
    public User() {}
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = ROLE_USER;
    }
    public User(String login, String password, String  firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.role = ROLE_USER;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String login;
    private Roles role;
    private String password;
    private String firstName;
    private String lastName;

    @Lob
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