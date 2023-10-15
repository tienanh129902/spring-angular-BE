package com.example.springbootAngular.controller;

import com.example.springbootAngular.dto.ApiResponseDTO;
import com.example.springbootAngular.dto.ErrorMessageDTO;
import com.example.springbootAngular.dto.post.PostDTO;
import com.example.springbootAngular.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> detail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.detail(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<PostDTO>> search(Pageable pageable) {
        return new ResponseEntity<>(postService.search(pageable), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> save(@RequestBody PostDTO dto) {
        ApiResponseDTO response = postService.save(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
