package example.실습.실습2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    /*
    day69 SPRING WEB2 ] 실습2 : 알림 소켓 만들기
    [ 조건 ]
    1. 최소 클래스 파일을 사용하여 구현 :
        AppStart (스프링 부트 실행 클래스)
        AlarmHandler (웹소켓 처리 클래스)
        WebSocketConfig (웹소켓 매핑 설정)
        실습2.html , 실습2.js
    2. 클라이언트가 웹소켓 서버에 접속하면, 현재 접속한 모든 클라이언트에게
        alert("익명의 유저가 접속했습니다.") 알림이 표시되도록 한다.
    3.  클라이언트가 연결을 종료했을 때는, 현재 접속한 모든 클라이언트에게
        alert("익명의 유저가 퇴장했습니다.") 알림이 표시되도록 한다.

[ 제출방법 ] 코드가 작성된 파일이 위치한 깃허브 상세 주소를 제출하시오.
     */

    // DI
    private final AlarmHandler alarmHandler;

    // 1. 서버웹소켓(핸들러) 객체를 스프링이 알 수 있게 경로 등록
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 개발자가 만든 서버 웹소켓(핸들러) 주소와 함께 등록
        // registry.addHandler( 서버웹소켓객체 , '경로' );
        registry.addHandler(alarmHandler , "/alarm");
    }
}
