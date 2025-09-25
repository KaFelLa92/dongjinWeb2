package example.day10;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/day10/trans")
@RequiredArgsConstructor
public class TransController {
    // DI
    private final TransService transService;

    // 1.
    // URL : http://localhost:8080/day10/trans
    @PostMapping("")
    public boolean trans1(){
        return transService.trans1();
    }

    // 2. 신동엽이 서장훈에게 10만원 보내는 예제
    // URL : http://localhost:8080/day10/trans/transfer
    // BODY : { "fromname" : "신동엽" , "toname" : "서장훈" , "money" : 100000 }
    @PostMapping("/transfer")
    public boolean transfer(@RequestBody Map<String , Object> transInfo){
        return transService.transfer(transInfo);
    }
}
