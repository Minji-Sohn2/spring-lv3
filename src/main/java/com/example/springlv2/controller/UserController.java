package com.example.springlv2.controller;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.LoginRequestDto;
import com.example.springlv2.dto.SignupRequestDto;
import com.example.springlv2.dto.UserInfoDto;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 할 때 사용자의 정보입력
    @PostMapping("/signup")
    public ApiResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("username과 password의 구성이 알맞지 않습니다.");
        }
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ApiResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }
}
