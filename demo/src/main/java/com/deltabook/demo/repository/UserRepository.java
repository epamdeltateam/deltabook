package com.deltabook.demo.repository;

import java.util.List;

import com.deltabook.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findByLastName(String lastName);

    @Query("from User where login=:login and password=:password")
    public Iterable<User> findLogPass(@Param("login") String login, @Param("password") String password );
}