package com.example.springlv2.jwt;

import com.example.springlv2.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // Header key 값 (cookie의 name 값)
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // token 식별자 규칙 (이 뒤는 token 이구나! value 앞에 붙이기, 한 칸 띄우기)
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분 (millisec)

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey(application.properties)
    private String secretKey;
    //secretkey 담을 객체 (jwt 암호화, 복호화에 사용)
    private Key key;
    // enum, 사용할 암호화 알고리즘
    public final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 잘 동작하는지 중간에 로그를 찍어볼 때 사용 -> @Slf4j
    public static final Logger logger = LoggerFactory.getLogger("과제 jwt 부분 관련 로그");

    // 한 번만 받아오면 되는 값을 더 요청하지 않도록하는
    // 생성자가 호출된 이후 (객체가 만들어진 이후) 코드 실행
    @PostConstruct
    public void init(){
        // secretKey : 이미 base64로 인코딩 된 값
        // 사용하려면 디코딩
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 id
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급 시간
                        .signWith(key,signatureAlgorithm) // 키, 암호화 알고리즘
                        .compact(); // 완성
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) { // 변조, 파괴
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        } catch (ExpiredJwtException e) { // 만료
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    // body 부분에서 claims(데이터들의 집합) 가져오기
    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
