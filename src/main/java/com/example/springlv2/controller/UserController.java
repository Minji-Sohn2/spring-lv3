package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.SignupRequestDto;
import com.example.springlv2.entity.User;
import com.example.springlv2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 할 때 사용자의 정보입력
    @PostMapping("/signup")
    public ApiResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return new ApiResponseDto("회원 가입 완료", HttpStatus.OK.value());
    }

    @PostMapping("/login")
    public ApiResponseDto login(@Valid @RequestBody SignupRequestDto signupRequestDto, HttpServletResponse res){
        try {
            // 받아온 데이터 signupRequetDto 검증하라고 보내주기
            // 검증 끝나면 jwt token을 cookie에 넣고
            // cookie를 담을 response 객체도 보내주기
            userService.login(signupRequestDto, res);
        } catch (Exception e) {
            // 로그인에 실패하면 메세지와 로그인 실패 상태코드 반환
            return new ApiResponseDto("로그인 실패", HttpStatus.UNAUTHORIZED.value());
        }
        // 로그인 성공하면 성공 메세지와
        return new ApiResponseDto("로그인 완료", HttpStatus.OK.value());
    }

}
