package example.실습.실습3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}

/*
    실습3 : 도서 대출 및 반납 트랜잭션 적용하기
    [조건1] AppStart , BookController , BookService , BookMapper (DTO 선택)
    [조건2] 
            도서대출 : 
                1. 대출 요청 시 해당 책의 재고를 1 감소한다. 재고가 0이면 예외발생
                2. 재고 감소 후 대출기록을 (등록) 처리한다. 대출기록 처리가 실패하면 예외발생
                3. 예외가 발생하면 전체 SQL 실행은 rollback한다.
            도서반납 :
                1. 반납 요청 시 해당 책의 재고를 1 증가한다. 만약 없는 책이면 예외발생
                2. 재고 증가 후 대출기록을 (업데이트) 처리한다. 만약에 대출기록이 없거나 이미 반납한 책이면 예외발생
                3. 예외가 발생하면 전체 SQL 실행은 rollback한다.
    [ 조건3 ] 트랜잭션 적용 :
    · BookService의 rentBook() / returnBook() 메소드에 @Transactional 어노테이션을 적용
    · 하나의 기능 내에서 SQL 실행 중 하나라도 실패하면 전체가 취소되어야 한다.

    [ 조건4 ] 샘플 SQL
    -- 1. 책 테이블
    CREATE TABLE books (
        id INT NOT NULL,
        title VARCHAR(255) NOT NULL,
        stock INT NOT NULL DEFAULT 0,
        PRIMARY KEY (id)
    );

    -- 2. 대출 기록 테이블
    CREATE TABLE rentals (
        id INT NOT NULL,
        book_id INT NOT NULL,
        member VARCHAR(100) NOT NULL,
        rent_date DATETIME DEFAULT NOW(),
        return_date DATETIME NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (book_id) REFERENCES books(id)
    );

    -- 3. 샘플 데이터 (책 목록)
    INSERT INTO books (id, title, stock) VALUES (1, '자바의 정석', 3);
    INSERT INTO books (id, title, stock) VALUES (2, '스프링 인 액션', 2);
    INSERT INTO books (id, title, stock) VALUES (3, '토비의 스프링', 1);
    INSERT INTO books (id, title, stock) VALUES (4, '리액트 교과서', 5);

    -- 4. 샘플 데이터 (대출 기록)
    INSERT INTO rentals (id, book_id, member) VALUES (1, 1, '홍길동');

    -- 5. 확인용 조회 쿼리
    SELECT * FROM books;
    SELECT * FROM rentals;

    [ 제출방법 ] 코드가 작성된 파일이 위치한 GitHub 상세 주소를 제출하시오.

*/
