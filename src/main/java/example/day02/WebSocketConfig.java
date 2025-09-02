package example.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// ws 프로토콜 통신이 왔을 때 특정한 핸들러(클래스)로 매핑/연결
@Configuration // 스프링 컨테이너(메모리) 빈(객체) 등록
@EnableWebSocket // 웹소켓 사용 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    // implements : 구현체. 오버라이딩(재정의) 해줘야 돌아감
    
    // DI
    final private ChatHandler chatHandler; // 서버웹소켓 객체

    // 1. 서버웹소켓(핸들러) 객체를 스프링이 알 수 있게 경로를 등록한다.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 개발자가 만든 서버웹소켓(핸들러)을 주소와 함께 등록한다.
        // registry.addHandler( 서버웹소켓객체 , "경로" );
        registry.addHandler(chatHandler , "/chat");
    }
} // class end
