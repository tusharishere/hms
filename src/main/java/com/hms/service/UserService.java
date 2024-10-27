package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private  AppUserRepository appUserRepository;
    private ModelMapper modelMapper;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }


    public AppUser createUser(UserDto userDto) {
        AppUser user = mapToEntity(userDto);
        AppUser savedUser = appUserRepository.save(user);
        return savedUser;

    }

    private UserDto mapToDto(AppUser user){
        UserDto userDto  = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private AppUser mapToEntity(UserDto userDto){
        AppUser user = modelMapper.map(userDto, AppUser.class);
        return user;
    }
}
