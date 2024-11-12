package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;
    private AppUserRepository appUserRepository;

    public UserController(UserService userService, AppUserRepository appUserRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        Optional<AppUser> byUserName = appUserRepository.findByUsername(userDto.getUsername());
        if(byUserName.isPresent()){
            return new ResponseEntity<>("Username is already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> byEmail = appUserRepository.findByEmail(userDto.getEmail());
        if(byEmail.isPresent()){
            return new ResponseEntity<>("Email is already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encryptedPw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPw);
        userDto.setRole("ROLE_USER");
        AppUser savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwnerUser(@RequestBody UserDto userDto) {
        Optional<AppUser> byUserName = appUserRepository.findByUsername(userDto.getUsername());
        if(byUserName.isPresent()){
            return new ResponseEntity<>("Username is already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> byEmail = appUserRepository.findByEmail(userDto.getEmail());
        if(byEmail.isPresent()){
            return new ResponseEntity<>("Email is already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encryptedPw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPw);
        userDto.setRole("ROLE_OWNER");
        AppUser savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> appUserDtos = userService.findAllUsers();
        return new ResponseEntity<>(appUserDtos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestParam Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        AppUser updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id) {
        UserDto appUserDto = userService.findUserById(id);
        return new ResponseEntity<>(appUserDto,HttpStatus.OK);
    }
}
