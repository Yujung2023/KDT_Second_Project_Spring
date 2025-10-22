package com.kedu.project.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
	@Value("${jwt.expiration}")
	private Long exp;
	
	private Algorithm algorithm;
	private JWTVerifier jwt;
	
	public JwtUtil(@Value("${jwt.secret}") String secret ) {
		this.algorithm = Algorithm.HMAC256(secret);
		this.jwt = JWT.require(algorithm).build();
	}
	
	public String createToken(String id,String name) {
		return JWT.create().
			   withSubject(id).
			   withClaim("name",name). //이름 추가했어요
			   withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis()+ exp * 1000)).
			   sign(this.algorithm);
	}
	
	public DecodedJWT verifyToken(String token) {
		return jwt.verify(token);
	}
	
    // JWT에서 userId(subject) 꺼내는 메서드 추가
    public String getUserId(String token) {
        DecodedJWT decoded = verifyToken(token);
        return decoded.getSubject();  
    }

    // Request 헤더에서 Bearer 토큰 꺼내는 메서드 추가
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

 // ✅ 토큰에서 UserId(sub) 추출
    public String extractUserId(String token) {
        DecodedJWT decoded = verifyToken(token);
        return decoded.getSubject();
    }

    // ✅ Request에서 UserId 추출 (컨트롤러는 이 메서드만 쓰면 됨)
    public String extractUserId(HttpServletRequest request) {
        String token = resolveToken(request);
        return extractUserId(token);
    }
}

