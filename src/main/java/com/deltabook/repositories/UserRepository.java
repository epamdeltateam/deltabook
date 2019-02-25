package com.deltabook.repositories;

import com.deltabook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLastName(String lastName);

    List<User> findByFirstName(String firstName);

    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    User findUserByLogin(String login);

    User findUserByLoginAndPassword(String login, String password);

}
