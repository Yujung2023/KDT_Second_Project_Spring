package com.kedu.project.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {
	@Value("${jwt.expiration}")
	private Long exp;
	
	@Value("${jwt.secret}")
	private String secret;
	
	private Algorithm algorithm;
	private JWTVerifier jwt;
	
	public JwtUtil() {
		this.algorithm = Algorithm.HMAC256(secret);
		this.jwt = JWT.require(algorithm).build();
	}
	
	public String createToken(String id) {
		return JWT.create().
			   withSubject(id).
			   withClaim("name","tom").
			   withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis()+exp)).
			   sign(this.algorithm);
	}
	
	public DecodedJWT verifyToken(String token) {
		return jwt.verify(token);
	}
}
