package com.example.springbootAngular.service;

import com.example.springbootAngular.dto.UserLoginDTO;
import com.example.springbootAngular.dto.UserRegisterDTO;

public interface AuthService {
    void signup(UserRegisterDTO dto);

    String login(UserLoginDTO dto);
}
