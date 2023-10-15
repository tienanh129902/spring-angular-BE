package com.example.springbootAngular.controller;

import com.example.springbootAngular.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @GetMapping()
    public ResponseEntity<ErrorMessageDTO> test() {
        return new ResponseEntity<>(new ErrorMessageDTO("Hi"), HttpStatus.OK);
    }
}
