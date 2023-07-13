package com.example.springlv2.repository;

import com.example.springlv2.entity.Comment;
import com.example.springlv2.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemoOrderByCreatedAtDesc(Memo memo);
}
