package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}")
    public ApiResponseDto postCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.postCommentLike(commentId, userDetails);
    }

    @PutMapping("/{commentId}")
    public ApiResponseDto updateCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.updateCommentLike(commentId, userDetails);
    }
}
