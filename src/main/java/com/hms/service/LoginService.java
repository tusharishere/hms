package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public LoginService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }


    public String verifyLogin(LoginDto loginDto){
        Optional<AppUser> byUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            AppUser user = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
                String token =  jwtService.generateToken(user.getUsername());
                return  token;
            }
        }
        else {
            return null;
        }
        return null;
    }
}
