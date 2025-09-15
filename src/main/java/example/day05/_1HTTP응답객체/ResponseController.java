package example.day05._1HTTP응답객체;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task/day05")
public class ResponseController {

    // [*] HTTP 응답객체
    // 1. Boolean

    @GetMapping("/bool")
    // URL : http://localhost:8080/task/day05/bool
    // public boolean task1(){
    public ResponseEntity< Boolean > task1() {
        // return false;
        return ResponseEntity.status(401).body(false); // 401 : 인증 실패 의미
    }

    // 2. Integer
    // URL : http://localhost:8080/task/day05/int
    @GetMapping("/int")
    public ResponseEntity< Integer > task2() {

        return ResponseEntity.status(202).body(1); // 202 : 응답 비동기 처리 중
    }

    // 3. String
    // URL : http://localhost:8080/task/day05/string
    @GetMapping("/string")
    public ResponseEntity< String > task3() {
        return ResponseEntity.status(201).body("qwe123"); // 201 : 응답 및 전송 성공
    }

    // 4. void
    // URL : http://localhost:8080/task/day05/void
    @GetMapping("/void")
    public ResponseEntity < Void > task4(){
        return ResponseEntity.status(403).build(); // 403 : 접근 권한 없음 , build() : 반환값 없음
    }

    // 5. Dto 또는 Map
    @GetMapping("/object")
    public ResponseEntity < Map <String , String >> task5(){
        Map<String , String > map = new HashMap<>();
        try{
            // 예] 강제로 예외 발생 : 500번 뜸
            // Integer.parseInt("a");
            map.put("이름" , "유재석");
            map.put("나이" , "43세");
            map.put("직업" , "코미디언");
            return ResponseEntity.status(200).body(map);
        } catch (Exception e){
            return ResponseEntity.status(500).build(); // 500 : 서버 오류
        } // catch end
    }


} // class end

/*
    boolean : 기본타입 (8가지)
    Boolean : 참조타입 (클래스 , 배열[] , 인터페이스 등)
        * 래퍼클래스 : 기본타입을 참조타입으로 사용하는 클래스들 : int -> Integer
 */
