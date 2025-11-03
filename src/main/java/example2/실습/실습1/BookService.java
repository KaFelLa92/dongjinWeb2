package example2.실습.실습1;

import example2.day01.ExamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    /*
    [조건 3] 서비스 구현 : Service 클래스를 작성하여 아래 기능을 구현한다.
    기능   설명
    도서 등록       새로운 도서 정보를 입력받아 DB에 저장
    도서 전체 조회   모든 도서 목록을 조회
    특정 도서 수정   도서ID를 기준으로 해당 도서 모든 정보 수정, @Transactional의 역할을 서술한다.
    특정 도서 삭제   도서ID를 기준으로 해당 도서 삭제
     */

    // [*] DI
    private final BookRepository bookRepository;

    // [1] 도서 등록 : 새로운 도서 정보를 입력받아 DB에 저장
    public BookEntity post ( BookEntity bookEntity) {
        // save : 저장 성공 시 성공된 엔티티 반환
        BookEntity saveEntity = bookRepository.save(bookEntity); // insert 자동 처리
        return saveEntity;
    }

    // [2] 도서 전체 조회 : 모든 도서 목록을 조회
    public List<BookEntity> get() {
        // findAll : 모든 엔티티 객체 반환
        List<BookEntity> entityList = bookRepository.findAll();
        return entityList;
    }

    // [3] 특정 도서 수정 : bookId를 기준으로 해당 도서 모든 정보 수정, @Transactional 역할 서술
    // 엔티티 setter 시 , 자동으로 DB 변경됨
    public BookEntity put (BookEntity bookEntity) {
        // 1) 수정할 엔티티를 bookId로 조회한다.
        Optional<BookEntity > optional = bookRepository.findById(bookEntity.getBookId());
        // 2) 본문이 존재하는지 검사한다.
        if (optional.isPresent()) {
            BookEntity entity = optional.get();             // 결과에 bookId가 존재하면
            entity.setBookName(bookEntity.getBookName());   // setter 이용하여 책이름 수정
            entity.setAuthor(bookEntity.getAuthor());       // 작가 수정
            entity.setPublisher(bookEntity.getPublisher()); // 출판사 수정
            return entity;
        }
        return bookEntity;
    }
    
    // [4] 특정 도서 삭제 : bookId를 기준으로 해당 도서 삭제
    public int delete (int bookId) {
        // deleteByUd ( pk번호 )
        bookRepository.deleteById(bookId);
        return 1;
    }

}
