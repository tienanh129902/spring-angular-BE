package com.example.springbootAngular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessTokenDTO {
    private String accessToken;
    private String username;
}
