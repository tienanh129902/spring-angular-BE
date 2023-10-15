package com.example.springbootAngular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO {

    private Integer status;
    private String message;
}
