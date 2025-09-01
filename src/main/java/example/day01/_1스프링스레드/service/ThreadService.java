package example.day01._1스프링스레드.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadService {

    // 1.
    public int thread1() {
        // 값 저장 변수
        int result = 0;
        // 반복문
        for (int i = 1; i <= 10; i++) {
            result += i; // 누적 더하기
            System.out.println(result);
            // 만약 서비스 처리가 늦어진다면 반환값이 다르게 나올까?
            // ex] 현재 스레드 1초간 일시정지
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        } // for end
        // 누적값 반환
        return result;
    } // func end

    // 2.
    @Async // 응답 먼저하고 내부적으로 처리하는 상황
    public void thread2() {
        int result = 0;
        for (int i = 1; i <= 10; i++) {
            result += i;
            System.out.println(result);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        } // func end
    } // func end

}
