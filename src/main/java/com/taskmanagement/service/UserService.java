package com.taskmanagement.service;

import com.taskmanagement.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    String registerUser(UserRequestDTO userRequestDTO);
    String login(LoginRequestDTO loginRequestDTO);

}
