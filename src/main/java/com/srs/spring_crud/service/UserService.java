package com.srs.spring_crud.service;

import com.srs.spring_crud.dto.UserDto;
import com.srs.spring_crud.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getUsers();

    User getUser(Integer id);

    void updateUser(Integer id, User user);

    void deleteUser(Integer id);
    // why using UserDTO?
    void updateName(Integer id, UserDto userDTO);
}
