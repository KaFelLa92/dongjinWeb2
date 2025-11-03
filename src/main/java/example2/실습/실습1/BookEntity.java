package example2.실습.실습1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // 엔티티 추가
@Data   // 롬복으로 필드값 읽어오기
public class BookEntity {
    /*
    [조건 1] 엔티티 설계 : “도서” 정보를 저장할 엔티티 클래스를 생성한다.
    각 필드의 역할과 데이터 타입을 적절히 설정한다., @Entity, @Id 등의 JPA 어노테이션을 활용한다.
    항목을 포함할 것: 도서ID, 도서명, 저자, 출판사
     */
    @Id // bookId를 primary key로 지정
    private int bookId;
    private String bookName;
    private String author;
    private String publisher;
}
