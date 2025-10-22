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
	
    
}

