package example.day01._1스프링스레드.controller;

import example.day01._1스프링스레드.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                 // 1. http 요청/응답 처리
@RequestMapping("/task/day01")  // 2. http 연결(매핑) 지원
@RequiredArgsConstructor        // 3. final 변수의 생성자(DI) 지원
public class ThreadController {

    // DI
    private final ThreadService threadService;

    // 1.
    // URL : http://localhost:8080/task/day01
    // BODY : 55 return
    @GetMapping // 동기화 : 스프링 매핑 컨트롤러는 자동 동기화 처리한다.
    // 먼저 요청을 한 HTTP부터 처리 후 반환한다.
    public int thread1() {
        System.out.println("ThreadController.thread1");
        return threadService.thread1();
    } // func end

    // 2.
    // URL : http://localhost:8080/task/day01
    @DeleteMapping
    public void thread2() {
        System.out.println("ThreadController.thread2");
        threadService.thread2();
    } // func end

} // class end
