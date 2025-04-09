package study.querydsl.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.querydsl.domain.user.entity.User;
import study.querydsl.domain.user.repository.UserRepository;
import study.querydsl.global.auth.CustomUserDetailsService;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";

    private static final long ACCESS_TOKEN_EXP = 60L * 30 * 4 * 1000;   // ms
    private static final long REFRESH_TOKEN_EXP = 60L * 60 * 24 * 7 * 1000;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(Long userId) {
        return TokenDto.builder()
                .accessToken(generateAccessToken(userId))
                .refreshToken(generateRefreshToken(userId))
                .accessTokenExp(LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXP / 1000))
                .refreshTokenExp(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXP / 1000))
                .build();
    }

    private String generateAccessToken(Long userId) {
        Date expiry = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP);
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim(AUTHORITIES_KEY, "jwt")
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Long userId) {
        Date expiry = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP);
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String subject = getAccessTokenSubject(removePrefix(token));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(removePrefix(token));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }

    public String getAccessTokenSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRefreshTokenSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Long getExpiration(String token) {
        return parseClaims(token).getExpiration().getTime();
    }

    public User findUserByToken(String token) {
        String subject = getAccessTokenSubject(removePrefix(token));
        return userRepository.findById(Long.parseLong(subject))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(removePrefix(token))
                .getBody();
    }

    public String removePrefix(String token) {
        return token.startsWith(BEARER_PREFIX) ? token.substring(BEARER_PREFIX.length()) : token;
    }
}

