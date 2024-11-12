package com.srs.spring_crud.controller;

import com.srs.spring_crud.dto.UserDto;
import com.srs.spring_crud.entity.User;
import com.srs.spring_crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // works
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully!");
    }

    // works
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    // works
    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Integer id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    // works
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok("User updated successfully!");
    }

    // works
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    // works
    @PatchMapping("/update-name/{id}")
    public ResponseEntity<String> updateName(@PathVariable Integer id, @Valid @RequestBody UserDto userDTO) {
        userService.updateName(id, userDTO);
        return ResponseEntity.ok("User name updated successfully!");
    }
}
