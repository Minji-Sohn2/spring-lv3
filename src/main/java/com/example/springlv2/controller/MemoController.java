package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.MemoRequestDto;
import com.example.springlv2.dto.MemoResponseDto;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    // final 로 선언했기 때문에 처음부터 초기화 되어야 함
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return memoService.createMemo(requestDto, userDetails);
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemoList() {

        return memoService.getMemoList();
    }

    @GetMapping("/memos/{id}")
    public MemoResponseDto getMemo(@PathVariable Long id){
        return memoService.getMemo(id);
    }

    @PutMapping("/memos/{id}")
    public MemoResponseDto updateMemo(
            @PathVariable Long id,
            @RequestBody MemoRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return memoService.updateMemo(id, requestDto, userDetails);
    }

    @DeleteMapping("/memos/{id}")
    public ApiResponseDto deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return memoService.deleteMemo(id, userDetails);
    }

}
