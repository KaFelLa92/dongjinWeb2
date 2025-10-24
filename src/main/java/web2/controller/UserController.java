package web2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.model.dto.UserDto;
import web2.service.JwtService;
import web2.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user") // 공통 URL 정의
@RequiredArgsConstructor
public class UserController {

    // [*] DI
    private final UserService userService;
    private final JwtService jwtService;

    // 1. 회원가입
    // URL : http://localhost:8080/api/user/signup
    // BODY : { "uid" : "qwe" , "upwd" : "1234" , "uname" : "유재석" , "uphone" : "010-1123-4123" , "urole" : "USER" }
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody UserDto userDto){
        int result = userService.signup(userDto);
        String bcrypt = userDto.getUpwd();
        return ResponseEntity.ok("반환할 PK 넘버 : " + result + " 암호화된 비밀번호 : " + bcrypt);
    }

    // 2. 로그인(+세션 : 자바웹서버(톰캣)의 임시저장소)
    // URL : http://localhost:8080/api/user/login
    // BODY : { "uid" : "qwe" , "upwd" : "1234" }
    // @PostMapping("/login")
    // public ResponseEntity<?> login (@RequestBody UserDto userDto , HttpSession session) {
    //    UserDto result = userService.login(userDto);
    //    if (result != null) { // 만약 로그인 성공하면 성공한 유저의 아이디를 세션에 저장
    //        session.setAttribute("loginUser" , result.getUid());
    //    }
    //    return ResponseEntity.ok(result);
    // }

    // 2-2. 로그인(+쿠키 : 클라이언트 브라우저의 임시 저장소 , 세션:서버 / 쿠키:클라이언트 )
    // URL : http://localhost:8080/api/user/login
    // BODY : { "uid" : "test1" , "upwd" : "1234" }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserDto userDto , HttpServletResponse response) {
       UserDto result = userService.login(userDto);
       if (result != null) { // 만약 로그인 성공하면 성공한 유저의 아이디를 세션에 저장
           // * 로그인정보를 세션에 저장하면 서버이므로 안전하다. 쿠키(클라이언트) 저장하면 위험하다.
           // 안전장치 필요하다.
           // Cookie cookie = new Cookie("쿠키명" , 값); 주로 사용자들의 설정값/임시 저장소
           // response.addCookie( 생성한쿠키 );
           // Cookie cookie = new Cookie("loginUser" , result.getUid()); // JWT로 다시 암호화

           // [*] JWT 로직 : 쿠키에 저장하는 회원정보를 토큰으로 저장하기
           Cookie cookie = new Cookie("loginUser" , jwtService.createToken(result.getUid() , result.getUrole()));


           // 클라이언트에서 해당 쿠키를 노출(탈취) 방지 = 주로 민감한 정보
           cookie.setHttpOnly( true ); // HTTP 암호화 : .setHttpOnly( true ) : 무조건 http에서만 사용. 즉) JS로 접근 불가능
           cookie.setSecure( false ); // http 탈취하더라도 암호화 , 단 https(true)에서만 가능
           //
           cookie.setPath("/"); // 쿠키에 접근할 수 있는 경로
           cookie.setMaxAge( 60 * 60 ); // 쿠키의 유효기간(초) , 1시간
           response.addCookie( cookie ); // 생성한 쿠키를 클라이언트에게 반환한다.
       }
       return ResponseEntity.ok(result);
    }

    // 3. 현재 로그인된 정보 호출( + 마이페이지 )
    // URL : http://localhost:8080/api/user/myinfo
    @GetMapping("/myinfo")
    public ResponseEntity<?> myInfo (HttpServletRequest request){ // 쿠키 활용한 로그인 상태를
        // 3-1 : 현재 클라이언트(브라우저) 저장된 모든 쿠키 가져오기
        Cookie[] cookies = request.getCookies();
        // 3-2 : 반복문 이용한 특정 쿠키명 찾기
        if ( cookies != null) {
            for (Cookie c : cookies) { // 쿠키 하나씩 반복하여 조회
                if (c.getName().equals("loginUser")) { // "loginUser" 쿠키명과 같다면
                    // ******* 쿠키의 저장된 토큰 반환 하기 *********
                    String token = c.getValue(); // 쿠키의 저장된 값 반환 ex] uid
                    boolean checked = jwtService.checkToken(token);
                    // 토큰이 유효하면
                    if (checked) {
                        String uid = jwtService.getUid( token ); // 토큰의 저장된 클레임(회원아이디) 추출하기
                        // 3-3 : 서비스를 호출하여 내 정보 조회
                        UserDto result = userService.myInfo(uid);
                        return ResponseEntity.ok(result); // 로그인 상태로 회원정보 조회
                    }
                }
            }
        }
        // 토큰이 유효하지 않으면
        return ResponseEntity.ok(null); // 토큰 검증 실패
    }

    // 4. 로그아웃
    // URL : http://localhost:8080/api/user/logout
    @GetMapping("/logout")
    public ResponseEntity<?> logout (HttpServletResponse response) {
        // 4-1 : 삭제할 쿠키명을 null값으로 변경
        Cookie cookie = new Cookie("loginUser" , null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath(("/"));
        cookie.setMaxAge(0); // 즉시 삭제하라는 뜻
        response.addCookie( cookie ); // 동일한 쿠키명으로 nul 저장하면 기존 쿠키명 사라진다.

        // 세션 : 서버에 저장하는 임시저장소 이므로 서버가 종료되면 사라진다.
        // 쿠키 : 클라이언트에 저장하는 임시 저장소이므로 서버가 종료되어도 유지된다.

        return ResponseEntity.ok( true );
    }

    // 5. 권한을 반환하는 컨트롤러
    @GetMapping("/check")
    public ResponseEntity<?> checkToken(
            @CookieValue(value = "loginUser" , required = false) // @CookieValue : 쿠키 관리하는 어노테이션
            String token) {
        Map<String , Object> map = new HashMap<>();
        if( token != null && jwtService.checkToken( token )) {  // 쿠키내 토큰이 유효하면
            String urole = jwtService.getUrole( token );        // 역할에 토큰 담기
            map.put("isAuth" , true);                           // 로그인 성공
            map.put("urole" , urole);
            return ResponseEntity.status(200).body(map);        // 유저가 로그인 했으면 반환
        } else {
            map.put("isAuth" , false);                          // 로그인 실패
            return ResponseEntity.status(403).body(map);        // 유저가 로그인 안했으면 반환 403 : 권한 없음
        }
    }




}
