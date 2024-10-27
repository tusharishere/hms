package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        AppUser savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
}
