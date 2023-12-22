package com.sparta.plusboard.post.controller;

import com.sparta.plusboard.global.exception.CommonResponseDto;
import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.global.security.UserDetailsImpl;
import com.sparta.plusboard.post.dto.PostRequestDto;
import com.sparta.plusboard.post.dto.PostResponseDto;
import com.sparta.plusboard.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestParam(value = "image", required = false) MultipartFile image, @RequestPart("data") @Valid PostRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException, CustomException {
        PostResponseDto responseDto = postService.createPost(requestDto, image, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 전체 게시글 목록 조회 + 페이징 정렬
    @GetMapping
    public List<PostResponseDto> getPosts(@PageableDefault(size = 5, sort = {"username", "createdAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PostResponseDto> page = postService.getPosts(pageable);
        return page.getContent();
    }

    // 단건 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) throws CustomException {
        PostResponseDto responseDto = postService.getPost(postId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException, CustomException {
        PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {
        postService.deletePost(postId, userDetails.getUser());

        return new ResponseEntity<>(new CommonResponseDto("게시글 삭제 완료", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
