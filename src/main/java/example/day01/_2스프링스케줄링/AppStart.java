package example.day01._2스프링스케줄링;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // 템플릿 1
@EnableScheduling // 비동기 사용 활성화
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class); // 템플릿 2
    } // main end
} // class end