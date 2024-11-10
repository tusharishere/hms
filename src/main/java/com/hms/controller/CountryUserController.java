package com.hms.controller;

import com.hms.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/country-user")
public class CountryUserController {

    @PostMapping("/user")
    public AppUser getUserCountry(
            @AuthenticationPrincipal AppUser user
    ){
        return user;
    }
}
