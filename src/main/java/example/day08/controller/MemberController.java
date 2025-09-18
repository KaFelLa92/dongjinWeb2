package example.day08.controller;

import example.day08.model.dto.MemberDto;
import example.day08.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:5173")
public class MemberController {
    // DI
    private final MemberService memberService;

    // [1] 등록
    // URL : http://localhost:8080/members
    // BODY : { "name" : "추성훈" , "phone" : "010-1231-1324" , "age" : 50 }
    @PostMapping("")
    public ResponseEntity<Integer> memberAdd (@RequestBody MemberDto memberDto){
        int result = memberService.memberAdd(memberDto);
        return ResponseEntity.status(200).body(result);
    }

    // [2] 전체조회
    // URL : http://localhost:8080/members
    @GetMapping("")
    public ResponseEntity<List<MemberDto>> memberPrint (){
        List<MemberDto> result = memberService.memberPrint();
        return ResponseEntity.status(200).body(result);
    }

    // [3] 삭제
    // URL : http://localhost:8080/members?mno=4
    @DeleteMapping("")
    public ResponseEntity<Integer> memberDelete (@RequestParam int mno){
        int result = memberService.memberDelete(mno);
        return ResponseEntity.status(200).body(result);
    }

}
