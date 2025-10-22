package web2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@Transactional
public class JwtService {

    // [1] 개발자가 비밀키 정의 , 32이상의 문자로 구성
    private String secret = "80419273645108293746518204937465";
    // [2] 개발자가 정의한 비밀키를 이용한 sha-256 알고리즘 적용
    private Key secretKey = Keys.hmacShaKeyFor( secret.getBytes( StandardCharsets.UTF_8) );

    // [3] 토큰 함수
    // [3-1] 토큰 생성 : 회원로그인정보 전용 ( 아이디, 권한 )
    public String createToken( String uid , String urole ){
        String token = Jwts.builder()           // 토큰객체 생성 빌더 함수 시작
                .claim("uid" , uid )         // uid라는 이름의 로그인 성공한 회원 아이디 저장
                .claim("urole" , urole)      // urole이라는 이름의 회원권한명 저장
                .setIssuedAt( new Date() )      // 발급 날짜/시간 , 현재 시스템(pc) 날짜/시간
                .setExpiration( new Date( System.currentTimeMillis() + 1000 * 60 * 60) ) // 1시간
                .signWith( secretKey , SignatureAlgorithm.HS256) // HS256 서명 알고리즘 적용
                .compact();                     // 토큰객체 생성 빌더 함수 종료
        System.out.println("token = " + token);
        return token;
    } // method end

    // [3-2] 토큰 검증
    public boolean checkToken (String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey) // 서명 검증시 필요한 비밀키 대입
                    .build()
                    .parseClaimsJws(token);    // 검증할 토큰 대입하여 검증 실행           
            return true;                      // 예외가 발생하지 않으면 검증 성공
        } catch (JwtException e) {
            throw new IllegalArgumentException("토큰이 없거나 유효기한이 만료되었습니다." ,e); // 예외가 발생하면 검증 실패 : 유효기간이 지났거나 존재하지 않음
        }
    }

    // [3-3] 토큰 클레임(값)들 추출
    public Claims getClaims (String token) {
        return Jwts.parser()
                .setSigningKey( secretKey )
                .build()
                .parseClaimsJws( token )
                .getBody(); // 검증 성공한 토큰의 (저장된데이터) 클레임들 반환

    }

    // [3-4] 클레임들의 특정 값 추출 getter 함수
    public String getUid( String token ) {
        return getClaims( token ).get( "uid" , String.class );
    }

    public String getUrole( String token ) {
        return getClaims( token ).get( "urole" , String.class );
    }




    // [JWT] : JSON(자바스크립트객체) WEB Token(징표)
    // 웹에서 자바스크립트 기반의 특정한 데이터를 대신하는 징표
    // 특정한 데이터를 직접적으로 보여주지 않고 징표를 대신 보여주는 구조
    // 웹에서 데이터 숨기기( 보안 )

    // JWT 토큰 가능하지만 , 데이터들 간의 서로 다른 토큰과 복잡한 계산식 추가하고 싶음
    // SHA-256 알고리즘 사용하여 비밀키(임의 , 개발자 만들기 , 토큰 풀기 위한 비밀번호)를 32글자 이상
    // 1-2 : 내가 만든 임의의 토큰을 해석하기 위한 계산식의 비밀키
    // private final String password = "80419273645108293746518204937465"; // 난수 키보다는 직접 생성한 키 권장
    // 1-3 : 비밀키에 알고리즘 적용하여 계산식(미리 태어난 사람 SHA) 생성 * 복호화는 안 됨.
    // private final Key secretKey = Keys.hmacShaKeyFor( password.getBytes( StandardCharsets.UTF_8 )); // UTF-8은 한글 쓰려고 넣음


    // [1] 토큰 생성
    //public String createToken(String data) {
    //    // 1-1 : 토큰 생성 : 라이브러리 설치
    //    String token = Jwts.builder()       // .builer() : 빌더패턴(생성자 대신에 함수형 객체생성) 이용한 토큰 생성
    //            .claim("key" , data)     // .claim( key , value ) : 토큰에 넣을 데이터 대입
    //            .setIssuedAt(new Date())    // .setIssuedAt( 토큰발급날짜/시간 )
    //                                        // .setExpiration( 토큰만료시간 )
    //                                        // .setExpiration( new Date(System.currentTimeMillis() + 1000 * 초) )
    //            .setExpiration( new Date( System.currentTimeMillis() + 1000 * 30) ) // 30초
    //            .signWith(secretKey)        // 알고리즘 이용한 토큰에 서명하기
    //            .compact();                 // 최종 JWT 문자열 형태로 생성
    //    System.out.println("token = " + token);
    //    return token;
    //} // method end
    //
    //// [2] 토큰 검증 : 토큰 유효성 검사(살았는지 죽었는지 확인)
    //public boolean checkToken(String token) {
    //    try {
    //        Jwts.parser()                       // parser 가져와서 분석하고 변환하는 과정
    //                .setSigningKey(secretKey)   // 서명 검증에 필요한 비밀키 대입
    //                .build()                    // 비밀키 확인
    //                .parseClaimsJws(token);     // 검증할 토큰 대입
    //        return true;                        // 예외가 발생하지 않으면 토큰 검증
    //    } catch (Exception e) {
    //        throw new IllegalArgumentException("토큰이 없거나 유효기한이 만료되었습니다." ,e);
    //    }
    //}
    //
    //// [3] 토큰 payload(내용물) claim 값 추출
    //public String valueExtract (String token) {
    //    try {
    //        Claims claims = Jwts.parser()
    //                .setSigningKey( secretKey )
    //                .build()
    //                .parseClaimsJws( token )
    //                .getBody();  // 검증 후 클레임(내용물) 가져오기
    //        System.out.println("claims = " + claims);
    //        String value = (String) claims.get("key");
    //        return value;
    //    } catch (Exception e) {
    //        return null;
    //    }
    //}
    

} // class end
// new Data() : 자바에서 시스템(컴퓨터) 시간을 반환하는 클래스
// 밀리초 : 1/1000초 , new Date(System.currentTimeMillis()) : 현재 날짜/시간을 밀리초 반환
