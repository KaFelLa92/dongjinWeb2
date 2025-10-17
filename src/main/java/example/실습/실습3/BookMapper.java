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
    int uploadBook(Map<String ,Object> book);
    int uploadAllBook(List<Map <String, Object>> bookList);

    // [6] 대출 기록 검색(동적쿼리) 대출한 사람 또는 대출한 도서명 조회, 조건 없으면 전체 조회
    List<Map <String , String>> viewRecord(String member , int bookId);

    // [*] 실습 5

    /*

    day92 SPRING WEB2 ] 실습5 : 도서 테이블 수정 및 서브쿼리 조회 XML 만들기
    [ 조건1 ] 반드시 XML 파일 이용하여 SQL 작성

    [ 조건2 ] ALTER 이용한 테이블 수정2개 controller/service/mapper
        1. 책books 테이블에 price 가격(int) 필드 추가
        2. 책books 테이블에 title 책이름 필드 (longtext) 필드 수정

    [ 조건3 ] 서브쿼리 이용한 메소드/기능 2개 controller/service/mapper
        1. 평균 재고보다 많은 재고를 가진 도서 조회
        2. 가장 많이 대출한 도서 조회

    [ 조건5 ] 실습3/4 과 동일 한 샘플 SQL

    [ 제출방법 ] 코드가 작성된 파일이 위치한 GitHub 상세 주소를 제출하시오.

     */

    // [1] 평균 재고보다 많은 재고를 가진 도서 조회


    // [2] 가장 많이 대출한 도서 조회



    /*

    day93 SPRING WEB2 ] 실습6 : 도서 view 생성 및 조회 기능 XML 만들기
    [ 조건1 ] 반드시 XML 파일 이용하여 SQL 작성

    [ 조건2 ] view 생성 기능 2개 controller/service/mapper <UPDATE>
        1. 대출기록 상세 뷰 생성 ( 책 + 대출기록 JOIN )
        2. 많은 재고를 가진 도서 조회 뷰 생성

    [ 조건3 ] 생성한 view 조회 기능 2개 controller/service/mapper
        1. 대출 상세 뷰 조회
        2. 많은 재고를 가진 도서 조회 뷰 조회

     */

    // [1] 대출기록 상세 뷰 생성
    int createDetailView(RentalsDto rentalsDto);

    // [2] 많은 재고를 가진 도서 조회 뷰 생성
    int createMostStockView(BooksDto booksDto);

    // [3] 대출기록 상세 뷰 조회
    RentalsDto getDetailView(int rentId);

    // [4] 많은 재고를 가진 도서 조회 뷰 조회
    BooksDto getMostStockView(int bookId);

}
