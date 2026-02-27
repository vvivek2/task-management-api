package com.taskmanagement.service;

import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.LoginResponseDTO;
import com.taskmanagement.dto.UserRequestDTO;
import com.taskmanagement.model.Role;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String registerUser(UserRequestDTO userRequestDTO) {
        if (repository.findByUsername(userRequestDTO.getUsername()).isPresent()) {
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
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        User userData = repository.findByUsername(loginRequestDTO.getUsername()).orElse(null);

        if (userData == null) {
            return new ResponseEntity<>(
                    new LoginResponseDTO("Invalid username", "Invalid username", null),
                    HttpStatus.FORBIDDEN
            );
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), userData.getPassword())) {
            return new ResponseEntity<>(
                    new LoginResponseDTO("Invalid password", "Invalid password", null),
                    HttpStatus.UNAUTHORIZED
            );
        }
        String userToken = jwtUtil.generateToken(loginRequestDTO.getUsername());
        return new ResponseEntity<>(
                new LoginResponseDTO("Login successful", "Login successful for " + loginRequestDTO.getUsername(), userToken),
                HttpStatus.OK
        );
    }
}
