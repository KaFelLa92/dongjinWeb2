package example.day06;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/day06/batis")
@RequiredArgsConstructor // final 변수에 대해 자동 생성자 만들어줌
public class BatisController {

    // * Mapper 객체 DI
    private final BatisMapper batisMapper;

    // 1. 학생 등록
    // URL : http://localhost:8080/day06/batis
    // BODY : { "name" : "유재슥" , "kor" : 97 , "math" : 86 }
    // RETURN : 1(성공) 0(실패)
    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody StudentDto studentDto){
        Integer result = batisMapper.save(studentDto);
        return ResponseEntity.status(200).body(result);
    }


    // 2. 전체 학생 조회
    // URL : http://localhost:8080/day06/batis
    @GetMapping("")
    public ResponseEntity<List<StudentDto>> findAll(){
        // 현재 예제는 서비스를 생략
        List<StudentDto> result = batisMapper.findAll();
        // ResponseEntity 응답객체
        return ResponseEntity.status(200).body(result);
    }

    // 3. 개별 학생 조회
    // URL : http://localhost:8080/day06/batis/find?sno=1
    @GetMapping("/find")
    public ResponseEntity<Map<String , Object>> find(@RequestParam int sno){
        Map<String , Object> result = batisMapper.find(sno);
        return ResponseEntity.status(200).body(result);
    }

    // 4. 개별 학생 삭제
    // URL : http://localhost:8080/day06/batis?sno=1
    // RETURN : 1(성공) 0(실패)
    @DeleteMapping("")
    public ResponseEntity<Integer> delete(@RequestParam int sno){
        int result = batisMapper.delete(sno);
        return ResponseEntity.status(200).body(result);
    }

    // 5. 개별 학생 수정
    // URL : http://localhost:8080/day06/batis
    // BODY : { "sno" : 2 , "name" : "유재슥" , "kor" : 97 , "math" : 86 }
    // RETURN : 1(성공) 0(실패)
    @PutMapping("")
    public ResponseEntity<Integer> update(@RequestBody StudentDto studentDto){
        Integer result = batisMapper.update(studentDto);
        return ResponseEntity.ok(result); // ok는 200번
    }


} // class end
