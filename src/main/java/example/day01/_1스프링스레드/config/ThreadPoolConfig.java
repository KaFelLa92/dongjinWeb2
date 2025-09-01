package example.day01._1스프링스레드.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration // 스프링 컨테이너 빈 등록 , IOC (서비스, 컨트롤러와 유사)
public class ThreadPoolConfig {

    // 1. 스프링 멀티스레드 커스텀 : 이 작업을 해야 홈페이지 터지는 걸 방지할 수 있다
    @Bean // 해당하는 메소드를 컨테이너 빈 등록하는 어노테이션
    public Executor taskExecutor() { // Executor : java.util로 import 해야함
        // 1) 객체 생성 ThreadPoolTaskExecutor : 스레드풀 작업스레드 객체
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize( 2 ); // 기본(최소) 실행 스레드 개수 설정
        executor.setMaxPoolSize( 10 ); // 최대 실행 스레드 개수 설정
        executor.setQueueCapacity( 20 ); // 최대 대기 개수 설정 , 20명 대기 상태가 넘치면 503 오류 발생
        executor.initialize();          // 스레드풀 초기화 : 서버 재실행마다 초기화
        return executor;
    }
} // class end
