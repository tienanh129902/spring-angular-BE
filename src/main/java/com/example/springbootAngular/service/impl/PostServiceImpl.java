package com.example.springbootAngular.service.impl;

import com.example.springbootAngular.dto.ApiResponseDTO;
import com.example.springbootAngular.dto.post.PostDTO;
import com.example.springbootAngular.exception.CustomBadRequestException;
import com.example.springbootAngular.exception.CustomForbiddenRequestException;
import com.example.springbootAngular.model.Post;
import com.example.springbootAngular.repository.PostRepository;
import com.example.springbootAngular.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseDTO save(PostDTO dto) {
        try {
            Post post;
            post = modelMapper.map(dto, Post.class);
            postRepository.save(post);
        } catch (Exception ex) {
            if (ex.getCause().equals(NullPointerException.class)) {
                ex.printStackTrace();
                throw new CustomBadRequestException(ex.getMessage());
            } else {
                ex.printStackTrace();
                throw new CustomForbiddenRequestException(ex.getMessage());
            }
        }
        return new ApiResponseDTO(HttpStatus.OK.value(), "Save successfully");
    }

    @Override
    public PostDTO detail(Long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            return modelMapper.map(post.get(), PostDTO.class);
        } catch (Exception ex) {
            throw new CustomBadRequestException(ex.getMessage());
        }
    }

    @Override
    public Page<PostDTO> search(Pageable pageable) {
        try {
            Page<Post> page = postRepository.findAll(pageable);
            List<PostDTO> data = page.getContent().stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .toList();
            return new PageImpl<>(data, page.getPageable(), page.getTotalElements());
        } catch (Exception ex) {
            throw new CustomBadRequestException(ex.getMessage());
        }
    }
}
