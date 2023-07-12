package com.example.springlv2.repository;

import com.example.springlv2.entity.Memo;
import com.example.springlv2.entity.MemoLike;
import com.example.springlv2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {
    Optional<MemoLike> findByMemoAndUser(Memo memo, User user);
}
