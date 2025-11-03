package example2.실습.실습1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {
    /*
    [조건 4] 컨트롤러 설계 : REST API 형식의 컨트롤러 클래스를 작성한다.
     */

    /*
    # [8-2] Entity 테이블 (존재하면) 수정 (존재하지 않으면) 생성
    spring.jpa.hibernate.ddl-auto=update
    이렇게 어플리케이션프로퍼티스 설정해놓고 ㄱㄱ
     */

    // [*] DI
    private final BookService bookService;
    
    // [1] 등록
    // URL : http://localhost:8080/library
    // BODY : { "bookId" : "1" , "bookName" : "칼의노래" , "author" : "김훈" , "publisher" : "민음사" }
    @PostMapping
    public ResponseEntity<?> post (@RequestBody BookEntity bookEntity) {
        BookEntity result = bookService.post(bookEntity);
        return ResponseEntity.ok(result);
    }


    // [2] 전체조회
    // URL : http://localhost:8080/library
    @GetMapping
    public ResponseEntity<?> get () {
        List<BookEntity> result = bookService.get();
        return ResponseEntity.ok(result);
    }
    
    // [3] 수정
    // URL : http://localhost:8080/library
    // BODY : { "bookId" : "1" , "bookName" : "연금술사" , "author" : "파울로코엘료" , "publisher" : "달밤지기" }
    @PutMapping
    public ResponseEntity<?> put (@RequestBody BookEntity bookEntity) {
        return ResponseEntity.ok(bookService.put(bookEntity));
    }
    
    // [4] 삭제
    // URL : http://localhost:8080/library?bookId=1
    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam int bookId) {
        return ResponseEntity.ok(bookService.delete(bookId));
    }

}
