package example.day03;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    // DI
    private final ChatSocketHandler chatSocketHandler;

    // 1. 서버웹소켓(핸들러) 객체를 스프링이 알 수 있게 경로 등록
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // registry.addHandler( 서버웹소켓객체 , '경로' );
        registry.addHandler(chatSocketHandler , "/chat");
    }
} // class end
