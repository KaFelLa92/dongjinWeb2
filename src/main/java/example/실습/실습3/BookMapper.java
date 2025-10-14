package example.실습.실습3;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    // [1] 대출 요청 (재고가 0보다 커야 발동. DB에서 unsigned로 이중 유효성검사)
    @Update("update books set stock = (stock - 1) where id = #{bookId} and stock > 0 ")
    int checkoutBook(int bookId);

    // [2] 대출 기록 등록
    @Insert("insert into rentals (book_id , member) values (#{bookId} , #{member}) ")
    int checkoutRecord(int bookId, String member);

    // [3] 반납 요청
    @Update("update books set stock = (stock + 1) where id = #{bookId} ")
    int turnbackBook(int bookId);

    // [4] 대출 기록 변경 ( return_date가 null이어야 반응하도록 유효성 검사)
    @Update("update rentals set return_date = now() where id = #{rentId} and return_date is null ")
    int turnbackRecord(int rentId);

    // [*] 실습 4
    
    /*
    [ 조건1 ] 반드시 XML 파일 이용하여 SQL 작성

    [ 조건2 ] 책 단일 등록 기능 구현( 생성된 도서번호 반환 )
    
    [ 조건3 ] 대출 기록 검색 (동적쿼리)
       - 대출한 사람 또는 대출한도서명 으로 조회
       - 조건이 없을 경우 전체 조회
    
    [ 조건4 ] 책 일괄 등록 기능 구현  (동적쿼리, 트랜잭션 적용)
       하나라도 실패 시 트랜잭션 적용
     */

    // [5, 7] 책 일괄 등록 기능 (제너레이트키 반환, 동적쿼리, 트랜잭션 적용)
    int uploadBook(List<Map <String, Object>> bookList);

    // [6] 대출 기록 검색(동적쿼리) 대출한 사람 또는 대출한 도서명 조회, 조건 없으면 전체 조회
    List<Map <String , String>> viewRecord(String member , int bookId);

}
