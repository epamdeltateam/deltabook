package com.deltabook.services;

import com.deltabook.model.User;
import com.deltabook.model.send.SendChangeUser;
import com.deltabook.model.send.SendSearchUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getUserByLogin(String login);

    String registerUser(User user);

    String updateUser(User newUser, User oldUser);

    void deleteUser(User user);

    User uploadAvatar(User user, MultipartFile file) throws Exception;

    void changeLastNameUser(SendChangeUser sendChangeUser, User user);

    void deleteUserTotal(User user);

    void deleteUserTemp(User user);

    List<User> getUserByNameSurnameOrNickname(SendSearchUser sendChangeUser);
}
