package com.example.springbootAngular.service;

import com.example.springbootAngular.dto.ApiResponseDTO;
import com.example.springbootAngular.dto.post.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    ApiResponseDTO save(PostDTO dto);
    PostDTO detail(Long id);
    Page<PostDTO> search(Pageable pageable);
}
