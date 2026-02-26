package com.taskmanagement.controller;

import com.taskmanagement.dto.JWTResponseDTO;
import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.UserRequestDTO;
import com.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task-management/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("register")
    public String registerUser(@Valid @RequestBody UserRequestDTO body){
        return userService.registerUser(body);
    }

    @PostMapping("login")
    public String loginUser(@Valid @RequestBody LoginRequestDTO body) {
        return userService.login(body);
    }



}
