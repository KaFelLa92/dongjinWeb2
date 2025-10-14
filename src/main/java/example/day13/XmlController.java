package example.day13;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class XmlController {
    // DI
    private final XmlMapper xmlMapper;

    // [1] 등록
    // http://localhost:8080/xml/
    // { "name" : "유재슥" , "kor" : 97 , "math" : 86 }
    @PostMapping("")
    public ResponseEntity<?> save (@RequestBody StudentDto dto){
        // < ? > : 제네릭타입에 ? 넣으면 모든 타입을 지칭한다. 와일드카드
        System.out.println("dto = " + dto); // soutp , save 실행전 dto = StudentDto(sno=0, name=유재슥, kor=97, math=86)
        xmlMapper.save(dto);                // SQL 실행
        System.out.println("dto = " + dto); // soutp , save 실행후 dto = StudentDto(sno=6, name=유재슥, kor=97, math=86) sno 제너레이트키 반환
        return ResponseEntity.ok(dto.getSno()); // 제네릭 ? 이므로 모든 자료 대입
    }

    // [2] 전체조회
    // http://localhost:8080/xml/
    @GetMapping("")
    public ResponseEntity<?> findAll(){
        List<StudentDto> result = xmlMapper.findAll();
        return ResponseEntity.ok( result );
    }

    // [3] 개별조회
    // http://localhost:8080/xml/find?sno=1
    @GetMapping("/find")
    public ResponseEntity<?> find(@RequestParam int sno){
        StudentDto result = xmlMapper.find(sno);
        return ResponseEntity.ok( result );
    }

    // [4] 개별삭제
    // http://localhost:8080/xml?sno=1
    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestParam int sno){
        int result = xmlMapper.delete(sno);
        return ResponseEntity.ok(result);
    }

    // [5] 개별수정
    // http://localhost:8080/xml/
    // { "sno" : 1 , "kor" : 97 , "math" : 86 }
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody StudentDto dto){
        xmlMapper.update(dto);
        return ResponseEntity.ok(true);
    }

    // [6-1] if 동적쿼리
    // http://localhost:8080/xml/query1?kor=90
    @GetMapping("/query1")
    public ResponseEntity<?> query1(@RequestParam int kor){
        List<StudentDto> result = xmlMapper.query1(kor);
        return ResponseEntity.ok(result);
    }

    // [6-2] if 동적쿼리
    // http://localhost:8080/xml/query2?kor=80
    @GetMapping("/query2")
    public ResponseEntity<?> query2(@RequestParam int kor){
        List<StudentDto> result = xmlMapper.query2(kor);
        return ResponseEntity.ok(result);
    }

    // [7] if 동적쿼리 2
    // http://localhost:8080/xml/query3?name=홍&math=60
    @GetMapping("/query3")
    public ResponseEntity<?> query3(@RequestParam String name , @RequestParam int math){
        StudentDto result = xmlMapper.query3(name , math);
        return ResponseEntity.ok(result);
    }
    
    // [8] foreach 동적쿼리
    // http://localhost:8080/xml/query4
    @PostMapping("/query4")
    public ResponseEntity<?> saveall(@RequestBody List<StudentDto> dtos){
        int result = xmlMapper.saveAll(dtos);
        return ResponseEntity.ok(result);
    }
    
    

    
} // class end
