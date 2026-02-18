package com.taskmanagement.service;

import com.taskmanagement.dto.JWTResponseDTO;
import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.UserRequestDTO;
import com.taskmanagement.model.Role;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String registerUser(UserRequestDTO userRequestDTO){
        if(repository.findByUsername(userRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(Role.USER);
        repository.save(user);
        return "User registered successfully";
    }

    @Override
    public JWTResponseDTO login(LoginRequestDTO loginRequestDTO){
        User userData = repository.findByUsername(loginRequestDTO.getUsername()).orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), userData.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        String userToken = jwtUtil.generateToken(loginRequestDTO.getUsername());
        return new JWTResponseDTO(userToken);
    }
}
