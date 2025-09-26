package example.실습.실습3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional // 트랜젝셔널로 예외 통과 못하면 DB에 변화 없음
public class BookService {
    // DI
    private final BookMapper bookMapper;

    // [1] 대출 메소드
    public int checkout(Map<String, Object> check) {
        // Map으로 받은 값 변수에 넣기
        int bookId = (Integer) check.get("bookId");
        String member = (String) check.get("member");

        // 2) 대출 기록 등록 (stock을 unsigned로 제약했을 때, 트랜잭션이 제대로 적용되어 대출기록이 재발생하지 않는가?)
        int checkoutRecord = bookMapper.checkoutRecord(bookId, member);

        // *) 강제 예외 발생
        if (checkoutRecord == 0) {
            throw new RuntimeException("대출 기록 등록 실패 : 기록이 존재하지 않습니다");
        }

        // 1) 대출 요청
        int checkoutBook = bookMapper.checkoutBook(bookId);

        // *) 강제 예외 발생
        if (checkoutBook == 0) {
            throw new RuntimeException("대출 요청 실패 : 재고가 없습니다");
        }

        return 1;
    } // method end

    // [2] 반납 메소드
    public int turnback(Map<String, Integer> turn) {
        // Map으로 받은 값 변수에 넣기
        int rentId = turn.get("rentId");
        int bookId = turn.get("bookId");

        // 1) 반납 요청 (반납 기록이 없을 때, 트랜잭션이 발동하여 책 재고가 계속 늘어나지 않는가?)
        int turnbackBook = bookMapper.turnbackBook(bookId);

        // *) 강제 예외 발생
        if (turnbackBook == 0) {
            throw new RuntimeException("반납 요청 실패 : 존재하지 않는 책입니다");
        }

        // 2) 대출 기록 변경
        int turnbackRecord = bookMapper.turnbackRecord(rentId);

        // *) 강제 예외 발생
        if (turnbackRecord == 0) {
            throw new RuntimeException("반납 기록 등록 실패 : 대출 기록이 없거나 이미 반납처리됨");
        }

        return 1;
    } // method end
} // class end
