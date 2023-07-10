package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.CommentRequestDto;
import com.example.springlv2.dto.CommentResponseDto;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{memoId}/comments")
    public CommentResponseDto createComment(@PathVariable Long memoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(memoId,requestDto, userDetails);
    }

    @PutMapping("/{memoId}/comments/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long memoId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return commentService.update(memoId, commentId, requestDto, userDetails);
    }

    @DeleteMapping("/{memoId}/comments/{commentId}")
    public ApiResponseDto deleteComment(
            @PathVariable Long memoId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return commentService.deleteComment(memoId, commentId, userDetails);
    }
}
