package example.day03;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

// 서버소켓 역할
@Component // MVC 패턴은 아니지만 스프링 컨테이너(메모리)의 빈(객체) 등록
public class ChatSocketHandler extends TextWebSocketHandler {

    // * 접속된 클라이언트소켓 명단 목록
    private static final Map<String , List<WebSocketSession>> list = new Hashtable<>();
    // { 0 : [ "유재석" , "강호동" ] , 1 : [ "서장훈" , "김희철" ] }
    // key : 방번호 , value : 해당 key(방)의 접속된 클라이언트들(리스트)
    
    // [*] JSON 타입을 자바 타입으로 변환해주는 라이브러리 객체 , ObjectMapper
    // 주요 메소드
    // 1. objectMapper.readValue ( json문자열 , Map.class ) : 문자열(json) -> Map
    // 2. objectMapper.writeValueAsString( map객체 ) : Map객체 -> 문자열(json)
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // [1] 클라이언트소켓과 서버소켓이 연동 되었을때 이벤트
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("*클라이언트소켓* 입장");
    }
    
    // [2] 클라이언트소켓과 서버소켓이 연동 끊겼을 때 이벤트
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("*클라이언트소켓* 퇴장");
        // 2-1 : 접속이 끊긴 세션(클라이언트소켓) 정보를 확인한다.
        String room = (String) session.getAttributes().get(("room")); // Object 타입이라, (String)으로 타입변환
        String nickName = (String) session.getAttributes().get("nickName"); // 강제타입변환 : (새로운타입)값 
        // => 닉네임은 유효성 검사 위한 것이지 별로 안 중요함
        // 2-2 : 만약에 방과 닉네임이 일치한 데이터가 접속명단에 존재하면
        if ( room != null && nickName != null) {
            List<WebSocketSession> dataList = list.get(room); // 해당 방의 key(방 번호) 접속(목록) 꺼내기
            dataList.remove( session );                       // 접속자 세션 만료시키기
            // 2-3 : 세션 제거를 성공했을 때 알림 메시지 보내기
            alarmMessage(room , nickName + "이 퇴장했습니다.");
        } // 2-2 if end

        //              저장                  불러오기
        // Map컬렉션 :  .put( key , value ) .get( key )
        // List컬렉션 : .add( value )       .get( 인덱스 )

    } // func end

    // [3] 클라이언트소켓으로부터 메시지 받았을때 이벤트
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("*클라이언트소켓*으로부터 메시지 받음");
        // 3-1 : 클라이언트 보낸 메시지
        System.out.println(message.getPayload()); // 메시지 확인
        // 3-2 : JSON 형식은 자바 기준으로 모르기 때문에 JSON 형식을 Map 타입으로 변환
        Map<String , String> msg = objectMapper.readValue(message.getPayload() , Map.class);
        // 3-3 : 만약 메시지 타입이 'join'이면
        if (msg.get("type").equals("join")){
            String room = msg.get("room"); // 방 번호
            String nickName = msg.get("nickName"); // 접속자 닉네임
            // 3-4 : 현재 메시지를 보내온 클라이언트소켓(세션)에 부가정보(방 번호와 접속자 닉네임) 추가 , 로그인 세션과 비슷
            session.getAttributes().put("room" , room);         // 브라우저세션 vs HTTP세션 vs 웹소켓세션
            session.getAttributes().put("nickName" , nickName);
            // 3-5 : 접속 명단에 등록하기
            if( list.containsKey( room ) ) { // 만약 등록할 방 번호(key)이 존재하면
                list.get( room ).add( session );
            } else { // 존재하지 않으면 방 생성해야함
                List<WebSocketSession> dataList = new Vector<>();
                dataList.add(session); // 새로운 목록에 세션 추가
                list.put( room , dataList ); // 새로운 방번호(key) 새로운 목록(list)을 map(접속명단)에 추가
            }
            System.out.println(list); // 같은 방에 여러 유저가 들어있을 때 확인
            //{1=[StandardWebSocketSession[id=d9611d90-58e9-9cbb-58dc-024ec71566df, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=bcc4043b-065e-af8a-e2d9-9a426e62eec7, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=b975e2e0-ce0c-a4ed-6ebf-f9982b852806, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=fbfd9841-cc15-6e38-7cfc-caf6e037451c, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=f79375fb-3817-10ae-7ffa-1797f012b510, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=d17dac14-fbe9-2737-e45f-a4bff0ad568a, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=2cb6e23e-dd00-3cd0-33f7-af9b1159c401, uri=ws://localhost:8080/chat],
            // StandardWebSocketSession[id=5e8c0481-f95e-e574-73c5-08e032f8baa9, uri=ws://localhost:8080/chat]],
            // 0=[StandardWebSocketSession[id=ffb54eaa-8513-a191-52ff-9c299e979918, uri=ws://localhost:8080/chat]]}
            // 한번에 여러 세션이 들어왔음을 알 수 있다.

            // 3-6 : 접속한 닉네임을 [4] 알림메시지 보내기
            alarmMessage( room , nickName + "이 입장했습니다.");
        } // 3-3 if end
        // 3-7 : 만약 메시지에서 타입(type)이 'msg'이면 
        else if ( msg.get("type").equals("msg")) {
            // 3-8 : 메시지를 보낸 세션의 방 번호 확인
            String room = (String) session.getAttributes().get("room");
            // 3-9 : 같은 방에 위치한 모든 세션들에게 받은 메시지 보내기
            for ( WebSocketSession client : list.get(room) ){
                client.sendMessage(message); // 서버가 받은 메시지를 클라이언트들에게 다시 전달
            } // for end
        } // 3-7 end
    } // func end
    
    // [4] 개발자가 만든 서비스 메소드 , 접속[3-6]/퇴장[2-3] 했을 때 실행
    public void alarmMessage( String room , String message ) throws Exception {
        // String room : 몇 번 방에? , String message : 메시지내용
        // throws : 예외처리 던지기 , 해당 메소드에서 모든 예외/오류를 해당 메소드를 호출한 곳으로 반환함 , try/catch로 대체 가능
        // 4-1 : 보내고자하는 정보를 map 타입으로 구성
        Map<String , String> msg = new HashMap<>();
        msg.put("type" , "alarm");
        msg.put("message" , message);
        // 4-2 : map 타입을 JSON 형식으로 변환 , objectMapper
        String sendMsg = objectMapper.writeValueAsString( msg );
        // 4-3 : 현재 같은 방에 위치한 모든 세션들에게 '알람' 메시지 보내기
        for (WebSocketSession session : list.get(room) ){
            session.sendMessage( new TextMessage( sendMsg ));
        }
    }
    
    
    
} // class end
