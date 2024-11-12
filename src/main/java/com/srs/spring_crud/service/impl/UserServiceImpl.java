package com.srs.spring_crud.service.impl;

import com.srs.spring_crud.dto.UserDto;
import com.srs.spring_crud.entity.User;
import com.srs.spring_crud.repository.UserRepository;
import com.srs.spring_crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        // Check if user already exists by name or any other unique attribute
        if (userRepository.existsByName(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this name already exists.");
        }

        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Override
    public void updateUser(Integer id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        // Set values to existing user
        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        existingUser.setAddress(user.getAddress());

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public void updateName(Integer id, UserDto userDTO) {
        // Retrieve the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        if (user.getName().equals(userDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a different name.");
        }
        user.setName(userDTO.getName());
        userRepository.save(user);
    }

}
