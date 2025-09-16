
# Spring Boot WebSocket 요약 정리 (day02 & day03 복습 자료)

이 문서는 `day02`의 기본 채팅과 `day03`의 다중 채팅방 구현을 바탕으로 Spring Boot에서 WebSocket을 설정하고 사용하는 방법을 요약합니다.

## 1. WebSocket 기본 개념

WebSocket은 단일 TCP 연결을 통해 서버와 클라이언트 간의 **전이중(full-duplex) 통신**을 제공하는 프로토콜입니다. HTTP와 달리 연결이 계속 유지되며, 양방향으로 실시간 데이터 전송이 필요할 때 (예: 채팅, 실시간 알림) 사용됩니다.

---

## 2. 핵심 구성 요소

Spring에서 WebSocket을 구현하기 위한 두 가지 핵심 요소는 `WebSocketConfigurer`와 `TextWebSocketHandler`입니다.

### 1) WebSocket 설정 (`WebSocketConfigurer`)

- **역할**: WebSocket을 활성화하고, 어떤 URL 요청을 어떤 핸들러가 처리할지 등록하는 설정 클래스입니다.
- **주요 어노테이션**:
    - `@Configuration`: 이 클래스가 Spring의 설정 파일임을 나타냅니다.
    - `@EnableWebSocket`: WebSocket 기능을 활성화합니다.

- **핵심 메소드**: `registerWebSocketHandlers`
    - 이 메소드를 오버라이드하여 WebSocket 핸들러와 엔드포인트(URL)를 매핑합니다.

```java
// day03/WebSocketConfig.java

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
        // "/chat" 경로로 오는 WebSocket 요청을 chatSocketHandler가 처리하도록 등록
        registry.addHandler(chatSocketHandler , "/chat");
    }
} 
```

### 2) WebSocket 핸들러 (`TextWebSocketHandler`)

- **역할**: WebSocket 연결, 메시지 수신, 연결 종료 등 실제 이벤트가 발생했을 때 실행될 로직을 정의하는 클래스입니다.
- `TextWebSocketHandler`를 상속받아 텍스트 기반의 메시지를 처리합니다.

---

## 3. `TextWebSocketHandler`의 주요 생명주기 메소드

`TextWebSocketHandler`를 상속받으면 다음과 같은 주요 메소드를 오버라이드하여 사용할 수 있습니다.

### 1) `afterConnectionEstablished(session)`
- **시점**: 클라이언트가 WebSocket 서버에 성공적으로 연결되었을 때.
- **주요 로직**: 접속한 클라이언트(`WebSocketSession`)를 접속자 명단에 추가합니다.

```java
// day02/ChatHandler.java - 단일 채팅방
private static final List<WebSocketSession> list = new Vector<>();

@Override
public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("[서버] 클라이언트소켓과 연동 성공");
    list.add(session); // 접속자 명단에 추가
}
```

### 2) `handleTextMessage(session, message)`
- **시점**: 클라이언트로부터 텍스트 메시지를 수신했을 때.
- **주요 로직**: 받은 메시지를 파싱하고, 특정 조건에 따라 다른 클라이언트들에게 메시지를 전송(브로드캐스팅)합니다.

```java
// day02/ChatHandler.java - 단일 채팅방
@Override
protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    System.out.println("[메시지확인] " + message.getPayload());
    // 접속된 모든 클라이언트에게 받은 메시지를 그대로 전송
    for (WebSocketSession clientSocket : list){
        clientSocket.sendMessage(message);
    }
}
```

### 3) `afterConnectionClosed(session, status)`
- **시점**: 클라이언트와의 WebSocket 연결이 끊어졌을 때.
- **주요 로직**: 접속자 명단에서 해당 클라이언트를 제거합니다.

```java
// day02/ChatHandler.java - 단일 채팅방
@Override
public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("[서버] 클라이언트소켓과 연동 종료");
    list.remove(session); // 접속자 명단에서 제거
}
```

---

## 4. 발전 과정: 단일 채팅방(`day02`) vs 다중 채팅방(`day03`)

`day03` 예제는 `day02`의 기본 구조를 확장하여 더 실용적인 기능을 구현합니다.

### 1) 접속자 관리: `List` -> `Map`
- **day02**: `List<WebSocketSession>`를 사용하여 모든 접속자를 하나의 목록으로 관리합니다. (모두가 같은 채팅방에 있음)
- **day03**: `Map<String, List<WebSocketSession>>`를 사용하여 **채팅방 별로 접속자 목록을 관리**합니다. `key`는 방 번호(`room`), `value`는 해당 방에 접속한 클라이언트 `List`가 됩니다. 이를 통해 다중 채팅방 구현이 가능해집니다.

```java
// day03/ChatSocketHandler.java
private static final Map<String , List<WebSocketSession>> list = new Hashtable<>();
```

### 2) 메시지 형식: `String` -> `JSON`
- **day02**: 단순 텍스트(`String`) 메시지를 주고받습니다.
- **day03**: **JSON 형식의 문자열**을 사용합니다. 이를 통해 메시지 `type`(`join`, `msg`, `alarm`), 보낸 사람(`nickName`), 내용(`message`) 등 구조화된 데이터를 주고받을 수 있습니다.
    - `ObjectMapper` 라이브러리를 사용하여 Java `Map` 객체와 JSON `String` 간의 변환을 쉽게 처리합니다.

```java
// day03/ChatSocketHandler.java
// JSON 문자열을 Map으로 변환
Map<String , String> msg = objectMapper.readValue(message.getPayload() , Map.class);

// 메시지 타입에 따라 다른 로직 처리
if (msg.get("type").equals("join")) {
    // 입장 처리
} else if (msg.get("type").equals("msg")) {
    // 메시지 처리
}
```

### 3) 세션에 부가 정보 저장
- **day03**: `session.getAttributes().put("key", "value")`를 사용하여 각 클라이언트 세션에 **부가적인 정보(메타데이터)를 저장**할 수 있습니다. 예제에서는 `room`(방 번호)과 `nickName`(닉네임)을 저장하여, 어떤 클라이언트가 어느 방에 어떤 이름으로 있는지 식별합니다.

```java
// day03/ChatSocketHandler.java - 입장 시
// 세션에 방 번호와 닉네임 저장
session.getAttributes().put("room" , room);
session.getAttributes().put("nickName" , nickName);

// day03/ChatSocketHandler.java - 퇴장 시
// 세션에서 방 번호와 닉네임 가져오기
String room = (String) session.getAttributes().get(("room"));
String nickName = (String) session.getAttributes().get("nickName");
```

---

## 5. 다중 채팅방 전체 흐름 요약 (`day03` 기준)

1.  **설정**: 서버가 시작되면 `WebSocketConfig`가 `/chat` 엔드포인트를 `ChatSocketHandler`에 매핑합니다.
2.  **연결**: 클라이언트가 `ws://서버주소/chat`으로 WebSocket 연결을 요청하면 `afterConnectionEstablished`가 호출됩니다.
3.  **입장**: 클라이언트는 연결 후 `{ "type":"join", "room":"1", "nickName":"유재석" }`과 같은 JSON 메시지를 서버로 보냅니다.
4.  **메시지 수신 (`handleTextMessage`)**:
    - 서버는 메시지를 받고 `ObjectMapper`로 파싱합니다.
    - `type`이 `join`이므로, 해당 `room` 번호를 `key`로 하여 `Map`에서 접속자 목록을 찾습니다.
    - 목록이 없으면 새로 만들고, 있으면 기존 목록에 클라이언트 세션(`session`)을 추가합니다.
    - `session.getAttributes()`에 `room`과 `nickName` 정보를 저장합니다.
    - 같은 방의 모든 클라이언트에게 "유재석이 입장했습니다."와 같은 `alarm` 메시지를 보냅니다.
5.  **채팅**: 클라이언트가 `{ "type":"msg", "message":"안녕하세요" }`와 같은 메시지를 보냅니다.
    - 서버는 `handleTextMessage`에서 메시지를 받고 `type`이 `msg`임을 확인합니다.
    - 메시지를 보낸 클라이언트의 `session`에서 `room` 번호를 가져옵니다.
    - `Map`에서 해당 `room`의 접속자 목록을 찾아, 목록에 있는 모든 클라이언트에게 받은 메시지를 그대로 전달합니다.
6.  **퇴장**: 클라이언트가 연결을 끊으면 `afterConnectionClosed`가 호출됩니다.
    - 서버는 해당 `session`의 `Attributes`에서 `room` 번호를 확인합니다.
    - `Map`에서 해당 `room`의 목록을 찾아, 목록에서 해당 `session`을 제거합니다.
    - 같은 방의 남은 클라이언트들에게 "유재석이 퇴장했습니다."와 같은 `alarm` 메시지를 보냅니다.
