package com.HCS_IDN.HCS_IDN;

import com.HCS_IDN.HCS_IDN.dtos.UserCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.UserDto;
import com.HCS_IDN.HCS_IDN.dtos.UserUpdateDto;
import com.HCS_IDN.HCS_IDN.models.User;
import com.HCS_IDN.HCS_IDN.repositories.UserRepository;
import com.HCS_IDN.HCS_IDN.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        User user1 = new User(1L, "testuser1", "test1@example.com", User.Role.CUSTOMER, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(2L, "testuser2", "test2@example.com", User.Role.STAFF, LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("testuser1", users.get(0).getUsername());
    }

    @Test
    void getUserById() {
        User user = new User(1L, "testuser1", "test1@example.com", User.Role.CUSTOMER, LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("testuser1", foundUser.getUsername());
    }

    @Test
    void getUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void createUser() {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setUsername("newuser");
        createDto.setEmail("new@example.com");
        createDto.setRole(User.Role.CUSTOMER);

        User savedUser = new User(1L, "newuser", "new@example.com", User.Role.CUSTOMER, LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto createdUser = userService.createUser(createDto);

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser() {
        User existingUser = new User(1L, "olduser", "old@example.com", User.Role.CUSTOMER, LocalDateTime.now(), LocalDateTime.now());
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setUsername("updateduser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserDto updatedUser = userService.updateUser(1L, updateDto);

        assertNotNull(updatedUser);
        assertEquals("updateduser", updatedUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
