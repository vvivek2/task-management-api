package com.taskmanagement.service;

import com.taskmanagement.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    String registerUser(UserRequestDTO userRequestDTO);
    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

}
