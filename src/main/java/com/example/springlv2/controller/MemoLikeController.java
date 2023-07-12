package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.MemoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memos/like")
public class MemoLikeController {

    private final MemoLikeService memoLikeService;

    @PostMapping("/{memoId}")
    public ApiResponseDto postCommentLike(@PathVariable Long memoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoLikeService.postMemoLike(memoId, userDetails);
    }

    @PutMapping("/{memoId}")
    public ApiResponseDto updateCommentLike(@PathVariable Long memoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoLikeService.updateMemoLike(memoId, userDetails);
    }
}
