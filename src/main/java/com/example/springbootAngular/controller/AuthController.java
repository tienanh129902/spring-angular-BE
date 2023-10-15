package com.example.springbootAngular.controller;

import com.example.springbootAngular.dto.AccessTokenDTO;
import com.example.springbootAngular.dto.UserLoginDTO;
import com.example.springbootAngular.dto.UserRegisterDTO;
import com.example.springbootAngular.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRegisterDTO dto) {
        authService.signup(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody UserLoginDTO dto) {
        String token = authService.login(dto);
        return new ResponseEntity<>(new AccessTokenDTO(token), HttpStatus.OK);
    }
}
