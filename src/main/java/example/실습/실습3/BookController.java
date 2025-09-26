package example.실습.실습3;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Log4j2 // 로그 처리하는 어노테이션 제공
public class BookController {
    // DI
    private final BookService bookService;

    // [*] print 대신 로그함수 사용해보기 = 부가기능 제공
    // URL : http://localhost:8080/book/log
    @GetMapping("/log")
    public void log() {
        System.out.println("개발 단계에서는 print 많이 써야 오류 포인트를 잡을 수 있다");
        // log.XXXX : 출력함수처럼 메시지 출력하는 함수이면서, 부가기능(파일처리, 제약조건) 있음
        log.debug( "테스트 과정에서 사용" ); // 테스트(개발) 과정에서 메시지 작성
        log.info( "서비스의 흐름, 데이터 상태 확인" ); // 운용 과정 메시지
        log.warn( "잠재적 문제에 사용 , 유지보수용" ); // 유지보수 과정 메시지
        log.error( "예외 또는 실패 상황" ); // 운용/유지보수 과정 메시지
    }
    

    // [1] 대출 메소드
    // URL : http://localhost:8080/book/rent
    // BODY : { "bookId" : 1 , "member" : "염현수" }
    @PostMapping("/rent")
    public ResponseEntity<?> checkout(@RequestBody Map<String, Object> check) {
        try {
            log.debug("[대여성공] " + check);
            return ResponseEntity.status(200).body(bookService.checkout(check));
        } catch (RuntimeException e) { // e.getMessage()에 예외 메시지 들어있음
            log.debug("[대여실패] " + e.getMessage() );
            return ResponseEntity.status(405).body("뭔가 문제가 있음"); // 405 : 잘못된 매개변수
        }
    }

    // [2] 반납 메소드
    // URL : http://localhost:8080/book/return
    // BODY : { "bookId" : 1 , "rentId" : 2 }
    @PostMapping("/return")
    public ResponseEntity<Integer> turnback(@RequestBody Map<String, Integer> turn) {
        try {
            return ResponseEntity.ok(bookService.turnback(turn));
        } catch (RuntimeException e) {
            return ResponseEntity.status(405).body(0);
        }
    }


} // class end
