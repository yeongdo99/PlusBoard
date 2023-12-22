package com.sparta.plusboard.post.service;

import com.sparta.plusboard.global.config.S3Uploader;
import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.global.exception.ErrorCode;
import com.sparta.plusboard.post.dto.PostRequestDto;
import com.sparta.plusboard.post.dto.PostResponseDto;
import com.sparta.plusboard.post.entity.Post;
import com.sparta.plusboard.post.repository.PostRepository;
import com.sparta.plusboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;

    // 글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, MultipartFile image, User user) throws CustomException, IOException {
        Post post = new Post(requestDto, user);

        if (image != null && !image.isEmpty()) {
            if (!image.getContentType().startsWith("image")) {
                throw new CustomException (ErrorCode.NOT_IMAGE_FILE);
            }
            String url = s3Uploader.upload(image, "images");
            post = postRepository.save(new Post(requestDto, user, url));
        } else {
            post = postRepository.save(post);
        }
        return new PostResponseDto(post);
    }

    // 전체 게시글 목록 조회 + 페이징 정렬
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDto::new);
    }


    // 단건 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        return new PostResponseDto(post);
    }

    
    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if (!post.getUser().getUsername().equals(user.getUsername())) { //
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    
    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, User user) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        postRepository.delete(post);
    }
}
