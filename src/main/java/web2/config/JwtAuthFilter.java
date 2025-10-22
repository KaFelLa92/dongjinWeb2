package web2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web2.service.JwtService;

import java.io.IOException;
import java.util.List;

@Component // 빈 등록
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    // 내가 만든 토큰(JwtService) 인증 방법을 (시큐리티방식) UsernamePasswordAuthenticationToken

    // [1] 내가 만든 토큰 방식
    private final JwtService jwtService;

    // [2] 기존 시큐리티 방식의 필터 커스텀 , 상속받기 (OncePerRequestFilter) , doFilterInternal 재정의
    @Override // 오버라이딩 ( 상속받은 메소드 재정의 )
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 커스텀하기
        // 1. 세션이 아닌 쿠키 방식이라, 쿠키 먼저 토큰 추출
        String token = null;
        if( request.getCookies() != null ) {                    // 쿠키 존재하면
            for (Cookie cookie : request.getCookies() ) {       // 모든 쿠키 반복문 돌리기
                if ( cookie.getName().equals("loginUser") ) {   // 로그인에서 만들어진 쿠키 존재하면
                    token = cookie.getValue();                  // 쿠키값(토큰) 꺼내기
                }
            }
        }
        
        // 2. UsernamePasswordAuthenticationToken을 재정의하기
        if( token != null && jwtService.checkToken( token ) ) { // 토큰이 유효하면
            String uid = jwtService.getUid( token );            // 아이디 꺼내기
            String urole = jwtService.getUrole( token );        // *권한* 꺼내기
            // [*] 시큐리티가 원하는 서명(UsernamePasswordAuthenticationToken) 만들어주기
            // new UsernamePasswordAuthenticationToken(
            //      id , password ,
            //      List.of ( new SimpleGrantedAuthority("ROLE_권한명1") , new SimpleGrantedAuthority("ROLE_권한명2") );

            // 2-2 : 시큐리티 토큰 생성
            UsernamePasswordAuthenticationToken t =
                    new UsernamePasswordAuthenticationToken(
                            uid , null ,
                            List.of( new SimpleGrantedAuthority("ROLE_"+urole) ) );
            System.out.println("t = " + t);
            // 2-3 : 시큐리티가 사용할 수 있게 토큰 저장 . SecurityContext( 시큐리티메모리 )
            SecurityContextHolder.getContext().setAuthentication( t );
        }

        // 3. 다른 필터에서 해당하는 토큰 필터를 호출할 수 있도록
        filterChain.doFilter( request , response );





    } // method end
} // class end
