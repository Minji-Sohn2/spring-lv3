package com.example.springlv2.security;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.LoginRequestDto;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // jwt 생성하기 위해
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login"); // 로그인 url 설정
    }

    // 로그인 시도
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // json 형태의 string 데이터를 객체로 변환 (request의 body 속 username, password -> dto)
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 인증 처리
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공
    // Authentication 인증 객체 받아옴 -> 그 속의 UserDetailsImpl username 가져오기
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{

        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        // token 생성
        String token = jwtUtil.createToken(username, role);

        // header 에 바로 넣어줌
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        ApiResponseDto apiResponseDto = new ApiResponseDto("로그인 성공", HttpServletResponse.SC_OK);
        String json = new ObjectMapper().writeValueAsString(apiResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

    // 로그인 실패
    // 반환 메세지 작성 시 여기서
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        response.setStatus(401); // unauthorized
        ApiResponseDto apiResponseDto = new ApiResponseDto("로그인 실패", HttpServletResponse.SC_UNAUTHORIZED);
        String json = new ObjectMapper().writeValueAsString(apiResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
