package example.day08.controller;

import com.github.dockerjava.api.model.Bind;
import example.day08.model.dto.MemberDto;
import example.day08.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> memberAdd(@RequestBody @Valid MemberDto memberDto, BindingResult bindingResult) {
        // 입력형식 유효성 검사
        if (bindingResult.hasErrors()) {
            // 필드 순서대로 유효성검사 실시
            String[] order = {"name", "phone", "age"};
            for (String field : order) {
                for (var error : bindingResult.getFieldErrors()) {
                    if (error.getField().equals(field)) {
                        return ResponseEntity.status(400).body(error.getDefaultMessage());
                    }
                }
            }
        }
        // 유효성 검사 통과 시 인트값 반환
        int result = memberService.memberAdd(memberDto);
        return ResponseEntity.status(200).body(result);
    }

    // [2] 전체조회
    // URL : http://localhost:8080/members
    @GetMapping("")
    public ResponseEntity<List<MemberDto>> memberPrint() {
        List<MemberDto> result = memberService.memberPrint();
        return ResponseEntity.status(200).body(result);
    }

    // [3] 삭제
    // URL : http://localhost:8080/members?mno=4
    @DeleteMapping("")
    public ResponseEntity<Integer> memberDelete(@RequestParam int mno) {
        int result = memberService.memberDelete(mno);
        return ResponseEntity.status(200).body(result);
    }

}
