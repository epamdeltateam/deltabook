package com.deltabook.services;


import com.deltabook.model.User;
import com.deltabook.model.send.SendChangeUser;
import com.deltabook.model.send.SendSearchUser;
import com.deltabook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public String registerUser(User user) {
        if (Objects.nonNull(userRepository.findUserByLogin(user.getLogin()))) {
            return "There is already a user with this login";
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.save(new User(user.getLogin(), hashedPassword, user.getFirstName(), user.getLastName()));
        return "Success";
    }

    @Override
    public String updateUser(User newUser, User oldUser) {
        if (passwordEncoder.encode(newUser.getPassword()).equals(passwordEncoder.encode(oldUser.getPassword()))) {
            return "Wrong Password";
        }
        if (Objects.nonNull(userRepository.findUserByLogin(newUser.getLogin())) && !userRepository.findUserByLogin(newUser.getLogin()).equals(oldUser)) {
            return "There is already a user with this login";
        }
        newUser.setId(oldUser.getId());
        userRepository.saveAndFlush(newUser);
        return "Success";
    }

    @Override
    public void deleteUser(User user) {
        user.setDeleted(true);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User uploadAvatar(User user, MultipartFile file) throws Exception {
        try {
            user.setPicture(file.getBytes());
            user = userRepository.saveAndFlush(user);
        } catch (IOException ex) {
            throw new Exception("Cannot read file");
        }
        return user;
    }

    @Override
    public void changeLastNameUser(SendChangeUser sendChangeUser, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setLastName(sendChangeUser.getNewLastName());
        userRepository.save(user);
    }

    @Override
    public void deleteUserTotal(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.delete(user);
    }

    @Override
    public void deleteUserTemp(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<User> getUserByNameSurnameOrNickname(SendSearchUser sendSearchUser) {
        User user;
        List<User> userList = new ArrayList<>();
        if (!sendSearchUser.getNickname().equals("")) {
            user = getUserByLogin(sendSearchUser.getNickname());
            if (Objects.nonNull(user)) {
                userList.add(user);
                return userList;
            } else {
                return null;
            }
        }
        if (!sendSearchUser.getName().equals("") && !sendSearchUser.getSurname().equals("")) {
            userList = userRepository.findByLastNameAndFirstName(sendSearchUser.getSurname(), sendSearchUser.getName());
            return userList;
        }
        if (sendSearchUser.getName().equals("") && !sendSearchUser.getSurname().equals("")) {
            userList = userRepository.findByLastName(sendSearchUser.getSurname());
            return userList;
        }
        if (!sendSearchUser.getName().equals("") && sendSearchUser.getSurname().equals("")) {
            userList = userRepository.findByFirstName(sendSearchUser.getName());
            return userList;
        }
        return null;
    }
}
