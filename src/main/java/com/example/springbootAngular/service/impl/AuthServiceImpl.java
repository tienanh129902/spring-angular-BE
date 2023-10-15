package com.example.springbootAngular.service.impl;

import com.example.springbootAngular.dto.UserLoginDTO;
import com.example.springbootAngular.dto.UserRegisterDTO;
import com.example.springbootAngular.exceptionHandler.CustomBadRequestException;
import com.example.springbootAngular.model.User;
import com.example.springbootAngular.repository.UserRepository;
import com.example.springbootAngular.security.JwtProvider;
import com.example.springbootAngular.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${custom-message.bad-credentials-message}")
    private String badCredentialMessage;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void signup(UserRegisterDTO dto) {
        dto.setPassword(encodePassword(dto.getPassword()));
        User user = modelMapper.map(dto, User.class);
        userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new CustomBadRequestException(badCredentialMessage));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomBadRequestException(badCredentialMessage);
        };

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }
}
