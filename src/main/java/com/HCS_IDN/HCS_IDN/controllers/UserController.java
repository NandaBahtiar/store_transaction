package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.UserCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.UserDto;
import com.HCS_IDN.HCS_IDN.dtos.UserUpdateDto;
import com.HCS_IDN.HCS_IDN.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
