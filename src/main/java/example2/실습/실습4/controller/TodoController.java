package example2.실습.실습4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import example2.실습.실습4.model.dto.TodoDto;
import example2.실습.실습4.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    /*
    [조건2] DTO / Repository / Service / Controller 설계
    - 등록 (createTodo), 전체조회 (getTodoList)
     */

    // [*] DI
    private final TodoService todoService;

    // [1] 등록
    // URL : http://localhost:8080/todo
    // BODY : { "title" : "점심 메뉴" , "content" : "룰렛으로 정해볼까요?" }
    // { "title" : "빵애에요" , "content" : "아 응애에요" }
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.createTodo(todoDto));
    }

    // [2] 전체조회
    // URL : http://localhost:8080/todo
    @GetMapping
    public ResponseEntity<?> getTodoList() {
        return ResponseEntity.ok(todoService.getTodoList());
    }


}
