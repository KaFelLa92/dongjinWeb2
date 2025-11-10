package example2.day04.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import example2.day04.model.dto.TodoDto;
import example2.day04.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    // [*] DI
    private final TodoService todoService;

    // URL : http://localhost:8080/api/todo/query1?title=아침 운동하기
    // [1] TodoRepository 2-1 , 3-1
    @GetMapping("/query1")
    public ResponseEntity<?> query1 (@RequestParam String title) {
        return ResponseEntity.ok( todoService.query1(title));
    }

    // URL : http://localhost:8080/api/todo/query2?title=아침 운동하기&content=헬스장에서 러닝머신 30분 뛰기
    // [2] TodoRespository 2-2 , 3-2
    @GetMapping("/query2")
    public ResponseEntity<?> query2 (@RequestParam String title , @RequestParam String content) {
        return ResponseEntity.ok(todoService.query2( title, content));
    }

    // URL : http://localhost:8080/api/todo/query3?title=기
    // [3] TodoRepository 2-3 , 3-3
    @GetMapping("/query3")
    public ResponseEntity<?> query3 (@RequestParam String title) {
        return ResponseEntity.ok(todoService.query3( title ));
    }

    // URL : http://localhost:8080/api/todo/page?page=1&size=3
    // [4] 페이징처리
    @GetMapping("/page")
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") int page ,    // 조회할 페이지 번호
            @RequestParam(defaultValue = "3") int size ) {  // 페이지에 조회할 자료 총 개수
        return ResponseEntity.ok( todoService.page( page, size ));
    }

    // URL : http://localhost:8080/api/todo/page2?keyword=기&page=2&size=3
    // URL : http://localhost:8080/api/todo/page2?keyword=&page=2&size=3    키워드 없으면 그냥 조회됨
    // [5] 키워드 페이징처리
    @GetMapping("/page2")
    public ResponseEntity<?> page2(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size){
        return ResponseEntity.ok( todoService.page2(keyword, page, size));
    }



}
