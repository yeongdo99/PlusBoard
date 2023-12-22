package com.sparta.plusboard.comment.controller;

import com.sparta.plusboard.comment.dto.CommentRequestDto;
import com.sparta.plusboard.comment.dto.CommentResponseDto;
import com.sparta.plusboard.comment.service.CommentService;
import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.global.security.UserDetailsImpl;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {
        CommentResponseDto commentResponseDto = commentService.createComment(postId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    // 댓글 조회 + 페이징 정렬
    @GetMapping("/comments")
    public List<CommentResponseDto> getComments(
            @PageableDefault(size = 5, sort = {"username", "createdAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CommentResponseDto> page = commentService.getComments(pageable);
        return page.getContent();
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
