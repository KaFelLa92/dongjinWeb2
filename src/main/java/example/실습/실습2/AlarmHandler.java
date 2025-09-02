package example.실습.실습2;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Vector;

@Component
public class AlarmHandler extends TextWebSocketHandler {
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

    // * 전체 클라이언트소켓 접속자 명단 목록을 List화
    private static final List<WebSocketSession> list = new Vector<>();
    // 상호통신이 가능한 동기화 상태를 만드려면 new Vector<>();

    // [1] 클라이언트소켓이 서버소켓으로부터 연결 성공했을 때 접속 알림
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("[서버] 익명의 유저가 클라이언트소켓에 접속");
        list.add(session);
        for (WebSocketSession clientSocket : list){ // 모든 클라이언트를 대상으로 알림 표시
            clientSocket.sendMessage(new TextMessage("익명의 유저가 접속했습니다."));
        }
    }

    // [2] 클라이언트소켓이 서버소켓으로부터 연결 종료했을 때 퇴장 알림
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("[서버] 익명의 유저가 클라이언트소켓에서 퇴장");
        list.remove(session);
        for (WebSocketSession clientSocket : list) { // 모든 클라이언트를 대상으로 알림 표시
            clientSocket.sendMessage(new TextMessage("익명의 유저가 퇴장했습니다."));
        }
    }

}
