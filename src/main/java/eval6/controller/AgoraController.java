package eval6.controller;

import eval6.model.dto.AgoraDto;
import eval6.service.AgoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eval6/agora")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:5173")
public class AgoraController {
    // DI
    private final AgoraService agoraService;

    // [4] 토론 글 작성
    // URL : http://localhost:8080/eval6/agora
    // BODY : { "cno" : "100002" , "acontent" : "눈 내리는 풍경에서의 복수극은 어떤 격언을 떠올리게 하네요 ㅎㅎ" , "apwd" : "0000" }
    // RETURN : 200037(PK값)
    @PostMapping("")
    public ResponseEntity<Integer> addAgora(@RequestBody AgoraDto agoraDto) {
        try {
            agoraService.addAgora(agoraDto);
            return ResponseEntity.status(200).body(agoraDto.getAno());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(0);
        }
    }

    // [5] 토론 글 삭제 (비번 기반)
    // URL : http://localhost:8080/eval6/agora?apwd=9999&ano=200028
    @DeleteMapping("")
    public ResponseEntity<Integer> deleteAgora(@RequestParam String apwd, @RequestParam int ano) {
        try {
            int result = agoraService.deleteAgora(apwd, ano);
            System.out.println(result);
            return ResponseEntity.status(200).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(0);
        }
    }

    // [6] 영화별 토론 전체 조회
    // URL : http://localhost:8080/eval6/agora/cine?cno=100008
    @GetMapping("/cine")
    public ResponseEntity<List<AgoraDto>> getAllAgoraSameCine(int cno) {
        List<AgoraDto> result = agoraService.getAllAgoraSameCine(cno);
        return ResponseEntity.status(200).body(result);
    }
    
    // [*] 토론 전체 조회
    // URL : http://localhost:8080/eval6/agora
    @GetMapping("")
    public ResponseEntity<List<AgoraDto>> getAllAgora(){
        List<AgoraDto> result = agoraService.getAllAgora();
        return ResponseEntity.status(200).body(result);
    }


}
