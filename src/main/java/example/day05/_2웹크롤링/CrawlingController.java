package example.day05._2웹크롤링;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task/day05")
@RequiredArgsConstructor
public class CrawlingController {
    // DI 스프링 컨테이너에서 빈(객체) 주입하기 (의존성 주입)
    private final CrawlingService crawlingService;

    // 1. 다음 날씨 정보 크롤링
    @GetMapping("/crawling1")
    // http://localhost:8080/task/day05/crawling1
    public Map<String , String> task1(){
        return crawlingService.task1();
    }

    // 2. CGV 영화 리뷰
    // http://localhost:8080/task/day05/crawling2
    @GetMapping("/crawling2")
    public List<String > task2(){
        return crawlingService.task2();
    }

}
