package com.sparta.plusboard.comment.service;

import com.sparta.plusboard.comment.dto.CommentRequestDto;
import com.sparta.plusboard.comment.dto.CommentResponseDto;
import com.sparta.plusboard.comment.entity.Comment;
import com.sparta.plusboard.comment.repository.CommentRepository;
import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.global.exception.ErrorCode;
import com.sparta.plusboard.post.entity.Post;
import com.sparta.plusboard.post.repository.PostRepository;
import com.sparta.plusboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    // 댓글 작성
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Comment comment = commentRepository.save(new Comment(requestDto, user, post));
        return new CommentResponseDto(comment);
    }

    // 댓글 조회 + 페이징 정렬
    public Page<CommentResponseDto> getComments(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(CommentResponseDto::new);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) throws CustomException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        comment.updateComments(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, User user) throws CustomException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        commentRepository.delete(comment);
    }

}
