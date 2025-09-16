package example.day07.controller;

import example.day07.model.dto.BoardDto;
import example.day07.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    // DI
    private final BoardService boardService;

    // [1] 등록
    // URL : http://localhost:8080/board
    // BODY : { "bcontent" : "반갑소잉" , "bwriter" : "김지석" }
    @PostMapping("")
    public ResponseEntity<Integer> boardWrite(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) {
        // 입력 형식 유효성 검사
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("[경고] " + error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(0);
        }
        int result = boardService.boardWrite(boardDto);
        return ResponseEntity.ok(result);
    }

    // [2] 전체조회
    // URL : http://localhost:8080/board
    @GetMapping("")
    public ResponseEntity<List<BoardDto>> boardPrint() {
        return ResponseEntity.status(200).body(boardService.boardPrint());
    }

    // [3] 개별조회
    // URL : http://localhost:8080/board/find?bno=1
    @GetMapping("/find")
    public ResponseEntity<BoardDto> boardFind(@RequestParam int bno) {
        return ResponseEntity.status(200).body(boardService.boardFind(bno));
    }

    // [4] 개별수정
    // URL : http://localhost:8080/board
    // BODY : { "bno" : 1 , "bcontent" : "오호홍햄빠끄" , "bwriter" : "염선생님" }
    @PutMapping("")
    public ResponseEntity<Integer> boardUpdate(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) {
        // 입력 형식 유효성 검사
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("[경고] " + error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(0);
        }
        return ResponseEntity.status(200).body(boardService.boardUpdate(boardDto));
    }

    // [5] 개별삭제
    // URL : http://localhost:8080/board?bno=2
    @DeleteMapping("")
    public ResponseEntity<Integer> boardDelete(@RequestParam int bno){
        return ResponseEntity.status(200).body(boardService.boardDelete(bno));
    }

}