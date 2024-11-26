package com.hms.controller;

import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.service.LoginService;
import com.hms.utils.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class LoginController {

    private LoginService loginService;
    private SmsService smsService;

    public LoginController(LoginService loginService, SmsService smsService) {
        this.loginService = loginService;
        this.smsService = smsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createLogin(@RequestBody LoginDto loginDto){
        String token = loginService.verifyLogin(loginDto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Invalid Username/Password",HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/login-otp")
    public String generateOtp(@RequestParam String mobileNumber) {
        smsService.generateAndSendOTP(mobileNumber);
        return "otp generated successfully";
    }
    @PostMapping("/validate-otp")
    public boolean validateOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        return smsService.validateOTP(phoneNumber, otp);
    }

}
