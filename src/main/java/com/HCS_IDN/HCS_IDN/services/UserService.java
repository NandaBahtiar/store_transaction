package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.UserCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.UserDto;
import com.HCS_IDN.HCS_IDN.dtos.UserUpdateDto;
import com.HCS_IDN.HCS_IDN.models.User;
import com.HCS_IDN.HCS_IDN.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return toDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDto createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setRole(userCreateDto.getRole());
        return toDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getRole() != null) {
            user.setRole(userUpdateDto.getRole());
        }
        return toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
