package com.sparta.plusboard.post.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plusboard.post.entity.Post;
import com.sparta.plusboard.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostsWithKeyword(String keyword) {
        return null;
    }

    @Override
    public Page<Post> customPosts(Pageable pageable) {
        QPost qPost = QPost.post;

        JPAQuery<Post> query = jpaQueryFactory.selectFrom(qPost)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 정렬 처리
        for (Sort.Order order : pageable.getSort()) {
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                    order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                    Expressions.stringPath(qPost, order.getProperty()));
            query.orderBy(orderSpecifier);
        }

        List<Post> posts = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }
}
