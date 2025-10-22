package com.kedu.project.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component // ✅ Spring이 자동으로 Bean으로 등록해주는 어노테이션 (다른 곳에서 @Autowired로 주입 가능)
public class JwtUtil {

	@Value("${jwt.expiration}") // ✅ application.properties 또는 yml에서 jwt.expiration 값을 주입받음 (만료시간)
	private Long exp;

	private Algorithm algorithm; // ✅ JWT의 서명 알고리즘 (여기선 HMAC256)
	private JWTVerifier jwt; // ✅ JWT 토큰을 검증할 때 사용하는 검증기 객체

	// ✅ 생성자에서 비밀키(secret)를 설정하고, 해당 키로 Algorithm 및 Verifier를 초기화
	public JwtUtil(@Value("${jwt.secret}") String secret ) {
		this.algorithm = Algorithm.HMAC256(secret); // ✅ 주입받은 secret으로 HMAC256 알고리즘 생성
		this.jwt = JWT.require(algorithm).build();  // ✅ 해당 알고리즘으로 JWT 검증기 생성
	}
	
<<<<<<< HEAD
	// ✅ JWT 생성 메서드: 사용자의 id를 기반으로 새로운 토큰을 만들어 반환
	public String createToken(String id) {
		return JWT.create(). // ✅ JWT 토큰 빌더 시작
			   withSubject(id). // ✅ 표준 클레임 중 하나인 subject(sub)에 id를 저장 (보통 사용자 식별자)
			   withClaim("name","tom"). // ✅ 커스텀 클레임 추가 (예시로 name: tom)
			   withIssuedAt(new Date()). // ✅ 토큰 발급 시각 (iat) 설정
			   withExpiresAt(new Date(System.currentTimeMillis()+exp)). // ✅ 토큰 만료 시각 (현재시간 + exp)
			   sign(this.algorithm); // ✅ 위에서 설정한 알고리즘으로 서명하여 토큰 문자열 반환
=======
	public String createToken(String id,String name) {
		return JWT.create().
			   withSubject(id).
			   withClaim("name",name). //이름 추가했어요
			   withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis()+ exp * 1000)).
			   sign(this.algorithm);
>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
	}
	
	// ✅ 전달받은 JWT 토큰의 유효성 검증 및 디코딩 (서명, 만료 등 확인)
	public DecodedJWT verifyToken(String token) {
		return jwt.verify(token); // ✅ 검증에 성공하면 DecodedJWT 객체 반환 (payload 내용 조회 가능)
	}
}
