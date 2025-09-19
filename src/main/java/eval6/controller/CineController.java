package eval6.controller;

import eval6.model.dto.CineDto;
import eval6.service.CineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eval6/cine")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:5173")
public class CineController {
    // DI
    private final CineService cineService;

    // [1] 영화 등록
    // URL : http://localhost:8080/eval6/cine
    // BODY : { "ctitle" : "퍼펙트데이" , "cdirector" : "고레와다 히로카즈" , "cgenre" : "드라마" , "ccontent" : " 잔잔한 영화" , "cpwd" : "1234" }
    // RETURN : 100012(PK값)
    @PostMapping("")
    public ResponseEntity<Integer> addCine(@RequestBody CineDto cineDto) {
        try {
            cineService.addCine(cineDto);
            return ResponseEntity.status(200).body(cineDto.getCno());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(0);
        }
    }

    // [2] 영화 삭제 (비번 기반)
    // URL : http://localhost:8080/eval6/cine?cpwd=1234&cno=100001
    @DeleteMapping("")
    public ResponseEntity<Integer> deleteCine(@RequestParam String cpwd, @RequestParam int cno) {
        try {
            int result = cineService.deleteCine(cpwd , cno);
            System.out.println(result);
            return ResponseEntity.status(200).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(0);
        }
    }

    // [3] 영화 목록 조회
    // URL : http://localhost:8080/eval6/cine
    @GetMapping("")
    public ResponseEntity<List<CineDto>> getAllCine() {
        List<CineDto> result = cineService.getAllCine();
        return ResponseEntity.status(200).body(result);
    }
}
