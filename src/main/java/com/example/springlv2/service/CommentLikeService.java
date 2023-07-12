package com.example.springlv2.service;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.entity.Comment;
import com.example.springlv2.entity.CommentLike;
import com.example.springlv2.repository.CommentLikeRepository;
import com.example.springlv2.repository.CommentRepository;
import com.example.springlv2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    public ApiResponseDto postCommentLike(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = findComment(commentId);
        CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser()).orElse(null);

        // 한 번도 좋아요를 누르지 않았을 경우
        if (commentLike == null) {
            // 새로 좋아요 객체를 만들어
            commentLike = new CommentLike(comment, userDetails.getUser());
        } else if(!commentLike.isLiked()){      // 좋아요 했었지만 취소된 상태
            commentLike.setLiked(true);
        }
        // 저장
        commentLikeRepository.save(commentLike);
        return new ApiResponseDto("좋아요", HttpStatus.OK.value());
    }

    @Transactional
    public ApiResponseDto updateCommentLike(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = findComment(commentId);
        CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser()).orElse(null);

        if (commentLike == null) {
            throw new IllegalArgumentException("한 번도 좋아요 한 적이 없는 댓글입니다.");
        } else if(commentLike.isLiked()){      // 좋아요 했었지만 취소된 상태
            commentLike.setLiked(false);
        }
        // 저장
        commentLikeRepository.save(commentLike);

        return new ApiResponseDto("좋아요 취소", HttpStatus.OK.value());
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}
