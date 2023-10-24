package com.example.springbootAngular.service.impl;

import com.example.springbootAngular.dto.AccessTokenDTO;
import com.example.springbootAngular.dto.user.UserLoginDTO;
import com.example.springbootAngular.dto.user.UserRegisterDTO;
import com.example.springbootAngular.exception.CustomBadRequestException;
import com.example.springbootAngular.model.User;
import com.example.springbootAngular.repository.UserRepository;
import com.example.springbootAngular.security.JwtProvider;
import com.example.springbootAngular.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void signup(UserRegisterDTO dto) {
        try {
            dto.setPassword(encodePassword(dto.getPassword()));
            User user = modelMapper.map(dto, User.class);
            userRepository.save(user);
        } catch (DataIntegrityViolationException dataEx) {
            if (dataEx.getMessage() != null && dataEx.getMessage().contains("user_username_index")) {
                throw new CustomBadRequestException("Duplicate username");
            } else {
                throw new CustomBadRequestException(dataEx.getMessage());
            }
        } catch (Exception ex) {
            throw new CustomBadRequestException(ex.getMessage());
        }
    }

    @Override
    public AccessTokenDTO login(UserLoginDTO dto) {
        User user = userRepository.findByUsernameAndActive(dto.getUsername(), true).orElseThrow(() ->
                new CustomBadRequestException(badCredentialMessage));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomBadRequestException(badCredentialMessage);
        };

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AccessTokenDTO(jwtProvider.generateToken(authentication), user.getUsername());
    }
}
