package example.실습.실습3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    // DI
    private final BookService bookService;

    // [1] 대출 메소드
    // URL : http://localhost:8080/book/rent
    // BODY : { "bookId" : 1 , "member" : "염현수" }
    @PostMapping("/rent")
    public int checkout(@RequestBody Map<String, Object> check){
        return bookService.checkout(check);
    }

    // [2] 반납 메소드
    // URL : http://localhost:8080/book/return
    // BODY : { "bookId" : 1 , "rentId" : 2 }
    @PostMapping("/return")
    public int turnback(@RequestBody Map<String , Integer> turn){
        return bookService.turnback(turn);
    }


} // class end
