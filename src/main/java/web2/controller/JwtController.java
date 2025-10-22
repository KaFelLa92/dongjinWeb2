package web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web2.service.JwtService;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JwtController {

    // [*] DI
    private final JwtService jwtService;

    // [1] 토큰 생성
    // URL : http://localhost:8080/api/jwt/create?data=사과
    // BODY : eyJhbGciOiJub25lIn0.eyJrZXkiOiLsgqzqs7wiLCJpYXQiOjE3NjEwOTQ4ODUsImV4cCI6MTc2MTA5NDk0NX0.
    // URL : http://localhost:8080/api/jwt/create?data=바나나
    // BODY : eyJhbGciOiJub25lIn0.eyJrZXkiOiLrsJTrgpjrgpgiLCJpYXQiOjE3NjEwOTQ5MjQsImV4cCI6MTc2MTA5NDk4NH0.
    // 공식에 따라 조금씩 달라짐
    //@GetMapping("/create")
    //public ResponseEntity<?> createToken(@RequestParam String data) {
    //    String token = jwtService.createToken(data);
    //    return ResponseEntity.ok( token );
    //}
    //
    //
    //// [2] 토큰 검증
    //// URL : http://localhost:8080/api/jwt/check?token=발급받은토큰
    //@GetMapping("/check")
    //public ResponseEntity<?> checkToken(@RequestParam String token) {
    //    boolean result = jwtService.checkToken(token);
    //    return ResponseEntity.ok( result );
    //}
    //
    //// [3] 토큰 값 추출
    //// URL : http://localhost:8080/api/jwt/value?token=발급받은토큰
    //// BODY : data 값을 뽑아옴
    //@GetMapping("/value")
    //public ResponseEntity<?> valueExtract (@RequestParam String token) {
    //    String value = jwtService.valueExtract(token);
    //    return ResponseEntity.ok(value);
    //}



}
