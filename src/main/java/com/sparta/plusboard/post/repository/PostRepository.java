package com.sparta.plusboard.post.repository;

import com.sparta.plusboard.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
    Page<Post> findAll(Pageable pageable);
    void deleteByModifiedAtBefore(LocalDateTime removeTime);

}
