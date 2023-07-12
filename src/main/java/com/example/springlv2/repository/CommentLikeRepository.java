package com.example.springlv2.repository;

import com.example.springlv2.entity.Comment;
import com.example.springlv2.entity.CommentLike;
import com.example.springlv2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
