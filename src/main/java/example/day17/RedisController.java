package example.day17;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("redis")
@RequiredArgsConstructor
public class RedisController {

    // [*] 간단한 텍스트를 레디스에 접근하는 객체
    private final RedisTemplate redisTemplate;
    private final RedisTemplate<String , Object > dtoRedisTemplate; // 키밸류로 저장

    // [1] 간단한 텍스트를 Redis 서버에 저장하기 (실제로 할 때는 서비스단에서 처리하기)
    // URL : http://localhost:8080/redis/test1
    // 반환 결과 : [ "80", "100" ]
    @GetMapping("/test1")
    public ResponseEntity<?> test(){
        StudentDto dto = new StudentDto();
        System.out.println("RedisController.test");
        // [저장] 레디스템플릿객체명.opsForValue().set( key , value ); , key값 중복 안 됨
        // { "유재석" : "90" } , { "강호동" : "80" }
        // redisTemplate.opsForValue().set(dto.getName() , dto.getKor());
        redisTemplate.opsForValue().set("유재석" , "90");  // 임의 데이터1
        redisTemplate.opsForValue().set("강호동" , "80");  // 임의 데이터2
        redisTemplate.opsForValue().set("유재석" , "100");  // key 중복 안 됨, value 중복 허용
        // [모든 키 호출] 레디스템플릿객체명.keys("*")     : 레디스에 저장된 모든 키 반환
        // [특정한 키의 값 호출] 레디스템플릿객체명.opsForValue().get( key );
        Set<String> keys = redisTemplate.keys("*");
            // List vs Map vs Set 컬렉션 프레임워크의 차이
        List<Object> result = new ArrayList<>(); // 임의 리스트
        for (String key : keys) {
            result.add(redisTemplate.opsForValue().get(key));
        }

        return ResponseEntity.ok(result);

    } // method end

    private final RedisTemplate<String , Object> studentTemplate;

    // [2] day13/day06 CRUD를 Redis로 변경 (서비스 로직에서 처리하는 게 맞음)
    // 실무에서는 무적권 DB로 씀(연습차원)
    // 1. 등록
    // URL : http://localhost:8080/redis
    // BODY : { "sno" : 1 , "name" : "유재석" , "kor" : "90" , "math" : "70" }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody StudentDto studentDto){
        System.out.println("studentDto = " + studentDto);
        // 0. 중복없는 key 구상
        String key = "student:"+studentDto.getSno(); // sno를 key로 조합해 중복 없는 key : student:1 , student:2
        // 1. 레디스에 전달받은 값 저장한다. key : dto 형식
        // { "sno" : 1 , "name" : "유재석" , "kor" : "90" , "math" : "70" }
        // 예상 : { "student:1" : { sno : 1 , name : "강호동" , math : 80 , kor : 100 } }
        studentTemplate.opsForValue().set( key , studentDto );
        return ResponseEntity.ok().body("[저장성공]");
    }

    // 2. 전체 조회
    // URL : http://localhost:8080/redis
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        // 0. 조회할 key를 모두 가져온다. * : 레디스내 모든 키 / xxxx:* : xxxx:동일한 * 자리는 임의의 문자 대응
        // studentTemplate.keys("문자열:*"); // 문자열까지는 동일하면 * 위치는 서로 다른 문자열 패턴
        Set<String> keys = studentTemplate.keys("student:*"); // student:1 , student:2
        // 1. 반복문 이용한 value 꺼내서 리스트에 담기
        List<Object> result = new ArrayList<>();
        for (String key : keys) {
            result.add(studentTemplate.opsForValue().get(key));
        }
        return ResponseEntity.ok().body(result);
    }

    // 3. 개별 조회
    // URL : http://localhost:8080/redis/find?sno=1
    @GetMapping("/find")
    public ResponseEntity<?> find ( @RequestParam int sno) {
        String key = "student:"+sno;                            // 1. 조회할 key 구상
        Object result = studentTemplate.opsForValue().get(key); // 2. 특정한 key의 value 호출
        return ResponseEntity.ok(result);
    }

    // 4. 개별 삭제
    // URL : http://localhost:8080/redis?sno=1
    @DeleteMapping("")
    public ResponseEntity<?> delete (@RequestParam int sno) {
        String key = "student:"+sno;                            // 1. 삭제할 key 구상
        boolean result = studentTemplate.delete(key);           // 2. 특정한 key를 이용한 엔트리(key-value 한 쌍) 삭제 , 템플릿객체명.delete( key );
        return ResponseEntity.ok(result);
    }

    // 5. 개별 수정
    // URL : http://localhost:8080/redis
    // BODY : { "sno" : 2 , "name" : "강호동" , "kor" : "100" , "math" : "100" }
    @PutMapping("")
    public ResponseEntity<?> update (@RequestBody StudentDto studentDto){
        String key = "student:"+studentDto.getSno();            // 1. 수정할 key 구상
        studentTemplate.opsForValue().set(key , studentDto);    // 2. 특정한 key를 덮어쓰기/수정
        return ResponseEntity.ok(true);
    }

    // * 인증코드 발급해서 레디스 유효기간 정하기
    // TTL : 레디스에 저장된 엔트리(key-value)를 특정한 기간(시간)이 되면 자동 삭제
    // URL : http://localhost:8080/redis/auth/send?phone=01012341234
    @GetMapping("/auth/send")
    public ResponseEntity<?> authSend(@RequestParam String phone){
        // 1. key 구상 , "auth:고객전화번호"
        String key = "auth:"+phone; // 번호마다 1개씩 인증코드
        // 난수 6자리
        String code = String.format("%06d" , new Random().nextInt(999999));
        // 2. 레디스에 인증코드 저장하기 , TTL(유효기간) , Duration.ofXXXX(수)
        redisTemplate.opsForValue().set(key , code , Duration.ofSeconds(10)); // 10초
        // API 이용하여 고객전화번호에게 인증코드 전송
        return ResponseEntity.ok().body("인증코드 발급 완료 : " + code);
    }

    // URL : http://localhost:8080/redis/auth/confirm?phone=01012341234&code=
    @GetMapping("/auth/confirm")
    public ResponseEntity<?> authConfirm(@RequestParam String phone , @RequestParam String code) {
        String key = "auth:"+phone; // 1. key 구상
        Object savedCode = redisTemplate.opsForValue().get(key); // 2. 조회할 key 이용한 value 호출
        if (savedCode == null && !savedCode.equals(code)){ // 저장된 코드가 없을 때
            return ResponseEntity.ok("인증실패 : 인증 만료 또는 코드 불일치");
        } else if (savedCode.equals(code)) { // 저장된 코드가 code와 일치할 때
            redisTemplate.delete(key); // 성공 시 인증코드 삭제
            return ResponseEntity.ok("인증성공");
        } else  {
            return ResponseEntity.ok().body("인증실패");
        }
    }

} // class end














