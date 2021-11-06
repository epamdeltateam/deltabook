package com.deltabook.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static com.deltabook.model.Roles.ROLE_USER;

@Entity
@Table(name = "users")
public class User {
    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = ROLE_USER;
        this.isBlocked = false;
        this.isDeleted = false;
    }

    public User(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.role = ROLE_USER;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBlocked = false;
        this.isDeleted = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private Roles role;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isBlocked;
    private boolean isDeleted;
    @Lob
    private byte[] picture;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @OneToMany(mappedBy = "friendFromId", cascade = CascadeType.ALL)
    private List<Contact> contacts_from;

    @OneToMany(mappedBy = "friendToId", cascade = CascadeType.ALL)
    private List<Contact> contacts_to;

    @OneToMany(mappedBy = "senderID", cascade = CascadeType.ALL)
    private List<Message> messages_sender;

    @OneToMany(mappedBy = "recipientID", cascade = CascadeType.ALL)
    private List<Message> messages_recipient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password);
    }
}