package example2.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamController {

    // [*] DI
    private final ExamService examService;

    // [1] 등록
    // URL : http://localhost:8080/api/exam
    // BODY : { "col1" : 4 , "col2" : "유재슥" , "col3" : 95.5 }
    @PostMapping("")
    public ResponseEntity<?> post (@RequestBody ExamEntity examEntity) {
        ExamEntity result = examService.post(examEntity);
        return ResponseEntity.ok(result);
    }

    // [2] 전체조회
    // URL : http://localhost:8080/api/exam
    @GetMapping("")
    public ResponseEntity<?> get () {
        List<ExamEntity> result = examService.get();
        return ResponseEntity.ok(result);
    }

    // [3] 수정
    // URL : http://localhost:8080/api/exam
    // BODY : { "col1" : "1" , "col2" : "무자식" , "col3" : "123214342.5" }
    @PutMapping
    public ResponseEntity<?> put (@RequestBody ExamEntity examEntity) {
        return ResponseEntity.ok(examService.put2(examEntity));
    }

    // [4] 삭제
    // URL : http://localhost:8080/api/exam?col1=2
    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam int col1) {
        boolean result = examService.delete(col1);
        return ResponseEntity.ok(result);
    }

} // class end
