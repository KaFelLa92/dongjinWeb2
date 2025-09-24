package example.day09;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

// [4] AOP 커스텀 , 
@Aspect // AOP 클래스를 스프링 AOP 컨테이너에 등록
@Component // AOP 클래스를 스프링 빈(객체) 컨테이너에 등록
class AopClass {
    // [4-1] 어플리케이션 내 AopService내 모든 메소드가 실행되면 같이 실행
    // @Before("execution( 경로와 조건 ")
    // * : 모든 리턴 타입의 메소드들
    // Java 이하 경로부터 : 적용한 메소드가 위치한 패키지/파일 경로
    // -> 같은 패키지 : 클래스명만 작성 AopService
    // -> 다른 패키지 : example.day09.AopService
    // .메소드명
    // .* " 해당 클래스내 메소드에 적용
    // .enter1 : 해당 클래스내 enter1에만 적용
    // ( 매개변수 )
    // .(..) : 해당 메소드의 모든 매개변수 적용
    // .enter11( int , boolean ) : 지정한 매개변수를 갖는 메소드만 적용
    @Before("execution( * AopService.*(..) )")
    public void check1() {
        System.out.println("[코로나] 열체크 / 로그인 기록 / 재고주문내역");
    }

    // [4-2]
    @After("execution( * AopService.*(..) )")
    public void check2() {
        System.out.println("[퇴장]");
    }

    // [4-3]
    // @After("execution( 리턴타입 패키지/클래스경로.메소드명( 매개변수 타입 , 매개변수 타입 )")
    @After("execution( * example.day09.AopService.enter1(..) )")
    public void check3() {
        System.out.println("[4-3] enter1에서 AOP"); // enter1(학원)에서만 실행됨
    }

    // [4-4] && args(인자들) 매개변수 값들을 연결/바인딩할 이름 정의
    @Before("execution( boolean example.day09.AopService.enter3( String ) ) && args(name) ")
    public void check4(String name) {
        System.out.println("[4-4] enter3에서 AOP [매개변수] : " + name);
    }

    // [4-5] @AfterReturning( returning = "바인딩할변수명 ) 리턴값을 반환받을 수 있다.
    @AfterReturning(
            value = "execution( boolean example.day09.AopService.enter3(..) )",
            returning = "result" // 리턴값 매핑/바인딩할 이름 정의
    )
    public void check5(boolean result) {
        System.out.println("[4-5] enter3에서 AOP [반환값] : " + result);
    }

    // [4-6] 개발자가 직접 메소드를 실행하는 시점
    @Around(" execution( * example.day09.AopService.enter3(..) ) " )
    public Object check6(ProceedingJoinPoint joinPoint) throws Throwable {
        // 리턴타입을 *으로 했으므로 모든 자료를 저장하기 위해 Object
        // 매개변수에는 'ProceedingJoinPoint'라는 비즈니스 로직과 조합
        System.out.println( "[4-6] joinPoint 객체" + joinPoint);                  // 1. 객체확인
        System.out.println( "[4-6] joinPoint 실행할 메소드명 " + joinPoint.getSignature());  // 2. 해당 AOP 메소드를 실행한 대상(메소드) 확인
        System.out.println( "[4-6] 실행할 메소드의 인자들 : " + Arrays.toString(joinPoint.getArgs()));        // 3. 실행한 대상(메소드) 매개변수의 인자들(배열) 확인
        Object result = joinPoint.proceed();// 4. 실행할 대상의 메소드를 직접 실행(실행 시점 커스텀 가능) 반환 받을 수 있음
        System.out.println("[4-6] 실행후 메소드의 반환값 : " + result);
        // 5. 실행한 대상 메소드의 리턴값을 그대로 리턴해야한다.
        return result;
    }


}

// [2] 간단한 HTTP 컨트롤러
@RestController
class AopController2 {
    @Autowired
    AopService aopService;

    // URL : http://localhost:8080/day09/aop
    @GetMapping("/day09/aop")
    public void method() {
        aopService.enter1(); // 서비스 메소드 호출
        aopService.enter2();
        aopService.enter3("유재석");
    }
}

// [3] 간단한 서비스
@Service
class AopService {
    public void enter1() {
        System.out.println("학원 입장");
    }

    public void enter2() {
        System.out.println("식당 입장");
    }

    public boolean enter3(String name) {
        System.out.println(name + " 헬스장 입장");
        return true;    // 임의 데이터
    }
}

// [1] 스프링 시작 클래스
@SpringBootApplication
public class Example2 {
    public static void main(String[] args) {
        SpringApplication.run(Example2.class);
    }
}

// AOP : 관점지향 프로그래밍
// 부가기능을 하나로 모듈화해서 핵심비즈니스 로직 분리
// 예] 로그(기록) , 트랜잭션(SQL시작/종료) , 보안(인증/권한)

// 1. AOP 라이브러리 설치 , https://start.spring.io/ 여기서는 없는게, 스프링에서 기본 지원함
// implementation 'org.springframework.boot:spring-boot-starter-web'을 build.gradle에 넣고 새로고침


