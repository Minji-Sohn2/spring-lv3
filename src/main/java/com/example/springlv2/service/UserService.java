package com.example.springlv2.service;

import com.example.springlv2.dto.SignupRequestDto;
import com.example.springlv2.entity.User;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.jwt.JwtUtil;
import com.example.springlv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    public User signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
//        String password = requestDto.getPassword();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        // requestDto의 adminToken이 비어있지 않다면
        if (!requestDto.getAdminToken().isEmpty()) {
            // 그리고 그 adminToken이 맞는 token이라면
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return user;
    }

}
