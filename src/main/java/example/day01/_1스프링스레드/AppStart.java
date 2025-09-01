package example.day01._1스프링스레드;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication // 템플릿 1
@EnableAsync // 비동기 사용 활성화
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class); // 템플릿 2
    } // main end
} // class end
