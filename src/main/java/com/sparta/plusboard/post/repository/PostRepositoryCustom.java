package com.sparta.plusboard.post.repository;

import com.sparta.plusboard.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {

    Page<Post> customPosts(Pageable pageable);

    List<Post> getPostsWithKeyword(String keyword);

}
