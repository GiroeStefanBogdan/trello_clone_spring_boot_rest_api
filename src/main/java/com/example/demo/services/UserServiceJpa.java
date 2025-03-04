package com.example.demo.services;

import com.example.demo.entities.AppUser;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.CreateUserDto;
import com.example.demo.model.UpdateUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class UserServiceJpa implements AppUserService, UserDetailsService {
    
    private final AppUserRepository appUserRepository;
    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> listAllUsers() {

        return appUserRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public CreateUserDto saveNewUser(CreateUserDto createUserDto) {
        AppUser user = userMapper.createUserDtoToUser(createUserDto);

//        user.setPass(passwordEncoder.encode(user.getPass()));
        AppUser savedUser = appUserRepository.save(user);
        return  userMapper.userToCreateUserDto(savedUser);
    }

    @Override
    public Optional<UpdateUserDto> updateUserById(UUID id, UpdateUserDto updateUserDto) {
        AtomicReference<Optional<UpdateUserDto>> atomicReference = new AtomicReference<>();

        appUserRepository.findById(id).ifPresent(foundUser -> {
            foundUser.setFirstName(updateUserDto.getFirstName());
            foundUser.setLastName(updateUserDto.getLastName());
            foundUser.setEmail(updateUserDto.getEmail());
            foundUser.setPass(updateUserDto.getPassword());

            atomicReference.set(Optional.of(userMapper.userToUpdateUserDto(appUserRepository.save(foundUser))));
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteUserById(UUID id) {
        if(appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(username);

        if(appUser != null) {

            return User.withUsername(appUser.getEmail())
                    .password(appUser.getPass())
                    .roles(String.valueOf(appUser.getRoles()))
                    .build();
        }
        return null;

    }
}
