package example.day01._2스프링스케줄링;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    
    // 1. 3초마다 자동 실행하는 서비스 메소드
    // @Scheduled ( fiexedRate = 밀리초 )
    @Scheduled( fixedRate = 3000 ) // 3초
    public void task1(){
        System.out.println("[3초] ScheduleService.task1");

    } //func end

    // 2. 5초마다 자동 실행하는 서비스 메소드
    final int fiveSeconds = 5000; // fixedRate에 변수는 안 들어가고 상수만 들어감
    @Scheduled( fixedRate = fiveSeconds )
    public void task2(){
        System.out.println("[5초] ScheduleService.task2");
    }
    
    // * (서버) 시스템 날짜/시간 기준으로 자동실행 스케줄링(백그라운드/비동기/멀티스레드)
    // 3. 현재 시스템의 매 시간 0:0:5초가 될 때마다 자동실행
    // ex] @Scheduled (cron = 초 분 시 월 일 요일 )
    @Scheduled( cron = "*/5 * * * * *" ) // 5초마다 , 0/5는 매분의 5초마다
    public void task3(){
        System.out.println("[cron5초] ScheduleService.task3");
    }

    @Scheduled( cron = " 0 */1 * * * *")    // 초를 0으로 바꾸면 기준이 0초 1분이 됨.
    public void task4(){
        System.out.println("[cron1분] ScheduleService.task4");
    }


} // class end
