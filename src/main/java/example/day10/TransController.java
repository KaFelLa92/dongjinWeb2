package example.day10;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/day10/trans")
@RequiredArgsConstructor
@Log4j2
public class TransController {
    // DI
    private final TransService transService;

    // [*] print 대신 로그함수 사용해보기 = 부가기능 제공
    // URL : http://localhost:8080/day10/trans/log
    @GetMapping("/log")
    public void log() {
        System.out.println("개발 단계에서는 print 많이 써야 오류 포인트를 잡을 수 있다");
        // log.XXXX : 출력함수처럼 메시지 출력하는 함수이면서, 부가기능(파일처리, 제약조건) 있음
        log.debug( "테스트 과정에서 사용" ); // 테스트(개발) 과정에서 메시지 작성
        log.info( "서비스의 흐름, 데이터 상태 확인" ); // 운용 과정 메시지
        log.warn( "잠재적 문제에 사용 , 유지보수용" ); // 유지보수 과정 메시지
        log.error( "예외 또는 실패 상황" ); // 운용/유지보수 과정 메시지
    }

    // 1.
    // URL : http://localhost:8080/day10/trans
    @PostMapping("")
    public boolean trans1(){
        return transService.trans1();
    }

    // 2. 신동엽이 서장훈에게 10만원 보내는 예제
    // URL : http://localhost:8080/day10/trans/transfer
    // BODY : { "fromname" : "신동엽" , "toname" : "서장훈" , "money" : 100000 }
    @PostMapping("/transfer")
    public boolean transfer(@RequestBody Map<String , Object> transInfo){
        return transService.transfer(transInfo);
    }
}
