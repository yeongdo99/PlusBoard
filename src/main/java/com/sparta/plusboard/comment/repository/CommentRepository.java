package com.sparta.plusboard.comment.repository;

import com.sparta.plusboard.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
