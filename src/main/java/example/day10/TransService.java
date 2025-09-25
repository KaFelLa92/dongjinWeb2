package example.day10;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional          // 트랜잭셔널 어노테이션 : 지정한 함수/클래스 내 모든 SQL 트랜잭션 적용 (클래스 또는 함수 위에 넣기)
public class TransService {
    // DI
    private final TransMapper transMapper;
    // 유재석과 강호동 insert하는 게 목적
    // 만약 한명이라도 insert 실패하면 취소(rollback)
    // ex) 누군가 은행에서 송금하면 한 명은 돈이 차감되고, 한 명은 그 금액만큼 돈이 올라야 정상 로직임

    // 트랜잭셔널을 넣으니, 강호동이 오류로 insert 되지 않자 유재석도 같이 insert 되지 않음
    // 1.
    public boolean trans1(){
        // [1-2] AOP가 Before로 먼저 commit 준비
        // 1) 유재석 insert
        transMapper.trans1("유재석"); // 정상
        // 2) 강호동 insert
        transMapper.trans2("강호동"); // 예제 특성상 억지로 비정상(예외처리)
        // SQLSyntaxErrorException 반환되면 AOP는 rollback 처리한다.
        // 앞전에 실행한 모든 SQL은 commit(완료) 안되므로, 없던 일이 된다.
        return true;
        // [1-2] AOP가 After로 처리후 commit 또는 rollback한다.
    } // func end

    // 입금 중 문제생겼을 때 한쪽 계좌만 늘거나 줄어드는걸 방지하기 위해 트랜잭셔널 사용
    // 지정한 함수내 예외(RuntimeException : 실행예외)가 발생하면 함수내 SQL 모두 취소
    // 2.
    public boolean transfer(Map<String , Object> transInfo){
        int money = Integer.parseInt(String.valueOf(transInfo.get("money")));   // 정수를 문자열로 타입변환
        // 1) 신동엽 10만원 차감
        String fromname = transInfo.get("fromname")+""; // 문자열로 타입변환 1
        boolean result1 = transMapper.withdraw( fromname , money);

        // *) 강제 예외 발생시켜서 rollback 내기
        if ( result1 == false ) { // 예외 발생했을 때
            throw new RuntimeException("차감 실패"); // throw new 예외클래스명 // 강제 예외 발생
        }
        // 결과 : 로직이 예외처리되었을 때 신동엽 10만원 깎인 것이 반영되지 않고 rollback됨.

        // 2) 서장훈 10만원 증가
        String toname = String.valueOf(transInfo.get("toname")); // 문자열로 타입변환 2
        boolean result2 = transMapper.deposit( toname , money);

        // *) 강제 예외 발생시켜서 rollback 내기
        if (result2 == false) {
            throw new RuntimeException("증감 실패"); // throw new 예외클래스명 // 강제 예외 발생
        }
        
        return true;
    }


} // class end
