package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private  AppUserRepository appUserRepository;
    private ModelMapper modelMapper;

    public UserService() {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }


    public AppUser createUser(UserDto userDto) {
        AppUser user = mapToEntity(userDto);
        AppUser savedUser = appUserRepository.save(user);
        return savedUser;

    }


    public List<UserDto> findAllUsers() {
        List<AppUser> appUsers = appUserRepository.findAll();
        List<UserDto> appUserDtos = appUsers.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        return appUserDtos;
    }

    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateUser(Long id, UserDto userDto) {
        AppUser user = appUserRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Record not found with this id")
        );
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setEmail(userDto.getEmail());

        String encryptedPw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPw);
        AppUser updatedUser = appUserRepository.save(user);
        return updatedUser;
    }

    public UserDto findUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Record not found")
        );
        UserDto appUserDto = mapToDto(appUser);
        return appUserDto;
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
