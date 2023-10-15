package com.example.springbootAngular.service;

import com.example.springbootAngular.dto.user.UserLoginDTO;
import com.example.springbootAngular.dto.user.UserRegisterDTO;

public interface AuthService {
    void signup(UserRegisterDTO dto);

    String login(UserLoginDTO dto);
}
