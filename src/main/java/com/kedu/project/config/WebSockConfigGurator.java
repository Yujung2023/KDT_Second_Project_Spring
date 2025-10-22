package com.kedu.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration // 이 클래스를 스프링 설정 빈으로 등록
@EnableWebSocketMessageBroker // STOMP 기반 메시지 브로커 사용을 활성화
public class WebSockConfigGurator implements WebSocketMessageBrokerConfigurer { // WebSocket 설정 클래스, 인터페이스 구현

    @Override // 메시지 브로커 동작 방식을 설정하는 메서드
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 구독하는 목적지(prefix)가 /topic 로 시작하면 내장 SimpleBroker가 처리
        // 예: /topic/chat/사용자ID 로 구독하면 서버가 여기에 메시지를 발행하여 브로드캐스트/개별전달 가능
        config.enableSimpleBroker("/topic");

        // 클라이언트가 서버로 메시지를 보낼 때 붙이는 prefix
        // 예: 클라이언트에서 /app/chat.send 로 전송하면 @MessageMapping("/chat.send")가 매핑됨
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override // STOMP(WebSocket) 연결 엔드포인트를 등록하는 메서드
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 최초로 WebSocket(SockJS) 연결을 맺을 URL 경로
        // 예: 프론트에서 SockJS("http://host:port/ws-chat") 로 접속
        registry.addEndpoint("/ws-chat")
                // CORS 허용. Spring 6+/Boot 3+에서는 setAllowedOriginPatterns("*") 권장
                // 개발 단계에서는 "*"로 두되, 운영에서는 특정 도메인만 허용하도록 제한하는 것이 안전
                .setAllowedOrigins("http://10.10.55.97:3000","http://192.168.219.108:3000")
                // 브라우저/프록시 환경에서 WebSocket 미지원 시 XHR-Streaming 등으로 대체해주는 폴백
                .withSockJS();
    }
}