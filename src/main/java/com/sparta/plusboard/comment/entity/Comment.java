package com.sparta.plusboard.comment.entity;

import com.sparta.plusboard.comment.dto.CommentRequestDto;
import com.sparta.plusboard.global.entity.TimeStamped;
import com.sparta.plusboard.post.entity.Post;
import com.sparta.plusboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comments;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.comments = requestDto.getComments();
        this.post = post;
        this.user = user;
    }

    public void updateComments(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }
}
