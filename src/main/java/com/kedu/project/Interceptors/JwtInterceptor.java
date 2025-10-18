package com.kedu.project.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kedu.project.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwt;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		System.out.println("jwt 인터셉터 동작 확인");

		String path = request.getRequestURI();
		System.out.println(path);

		String method = request.getMethod();
		System.out.println(method);

		if(method.equals("OPTIONS")) {
			return true; //OPTIONS로 오는건 무조건 열어주겠다.
			//preflight 보안 패킷은 그냥 허용
		}

		if(method.equals("POST") && path.equals("/auth")) {
			return true;
			//post인 메서드는 그냥 보내줘라
		}



		String authHeader = request.getHeader("Authorization");

		if(authHeader == null )
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Token Error");
			return false;
		}
		
		String token = authHeader.substring(7);
		if(token.split("\\.").length != 3) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token format");
		    return false;
		}
		
		try{
			DecodedJWT djwt = jwt.verifyToken(token);
			//토큰이 정상이면 동작
			request.setAttribute("loginID",djwt.getSubject());
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token Error");
			return false;
			//false가 인터셉터로 막는 기능.
		}


	}
}

