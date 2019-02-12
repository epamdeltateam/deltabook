package com.deltabook.demo.repository;

import java.util.List;

import com.deltabook.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findByLastName(String lastName);
}