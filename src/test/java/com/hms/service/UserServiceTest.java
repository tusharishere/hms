package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private AutoCloseable autoCloseable;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        appUser = new AppUser();
        appUser.setName("user");
        appUser.setUsername("testUser");
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");
        appUser.setRole("USER");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    // Test case for creating a user
    @Test
    void testCreateUser() {

        // Arrange: Mock the repository behavior
        when(modelMapper.map(any(UserDto.class), eq(AppUser.class))).thenReturn(appUser);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        // Act: Call the service method
        AppUser createdUser = userService.createUser(new UserDto("user", "testUser", "test@example.com", "password", "USER"));

        // Assert: Verify the outcome
        assertNotNull(createdUser);
        assertThat(createdUser.getUsername()).isEqualTo("testUser");
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
        assertThat(createdUser.getPassword()).isEqualTo("password");
        assertThat(createdUser.getRole()).isEqualTo("USER");

    }

    // Test case for finding all users
    @Test
    void testFindAllUsers() {
        // Arrange: Mock the repository behavior
        when(appUserRepository.findAll()).thenReturn(List.of(appUser));

        // Mock the mapping from AppUser to UserDto
        UserDto userDto = new UserDto("user", "testUser", "test@example.com", "password", "USER");
        when(modelMapper.map(any(AppUser.class), eq(UserDto.class))).thenReturn(userDto);

        // Act: Call the service method
        List<UserDto> allUsers = userService.findAllUsers();

        // Assert: Verify the outcome
        assertNotNull(allUsers);
        assertThat(allUsers.size()).isEqualTo(1);
        assertThat(allUsers.get(0).getUsername()).isEqualTo("testUser");
        assertThat(allUsers.get(0).getEmail()).isEqualTo("test@example.com");

    }

    // Test case for deleting a user
    @Test
    void testDeleteUser() {
        // Arrange: Mock the repository behavior
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(appUser));

        // Act: Call the service method
        userService.deleteUser(1L);

        // Assert: Verify the outcome
        verify(appUserRepository, times(1)).deleteById(anyLong());
    }

    // Test case for updating a user
    @Test
    void testUpdateUser() {
        // Arrange: Mock the repository behavior
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(appUser));
        when(modelMapper.map(any(UserDto.class), eq(AppUser.class))).thenReturn(appUser);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        // Act: Call the service method
        AppUser updatedUser = userService.updateUser(1L, new UserDto("updatedUser", "updatedTestUser", "updateTest@example.com", "updatePassword", "USER"));

        // Assert: Verify the outcome
        assertNotNull(updatedUser);
        assertThat(updatedUser.getUsername()).isEqualTo("updatedTestUser");
        assertThat(updatedUser.getEmail()).isEqualTo("updateTest@example.com");
        assertThat(updatedUser.getPassword()).isNotNull(); // Ensure password is hashed and not null
        assertThat(updatedUser.getPassword()).startsWith("$2a$"); // Optional: Verify the password format for BCrypt
        assertThat(updatedUser.getRole()).isEqualTo("USER");
    }

    // Test case for finding a user by ID
    @Test
    void testFindUserById() {
        // Arrange: Mock the repository behavior
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(appUser));

        // Mock the mapping from AppUser to UserDto
        UserDto userDto = new UserDto("user", "testUser", "test@example.com", "password", "USER");
        when(modelMapper.map(any(AppUser.class), eq(UserDto.class))).thenReturn(userDto);

        // Act: Call the service method
        UserDto foundUser = userService.findUserById(1L);

        // Assert: Verify the outcome
        assertNotNull(foundUser);
        assertThat(foundUser.getUsername()).isEqualTo("testUser");
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
