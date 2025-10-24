package web2.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web2.service.JwtService;
import web2.service.UserService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler { // [1] AuthenticationSuccessHandler 구현체 만들기

    // [*] DI
    private final JwtService jwtService; // 토큰서비스
    private final UserService userService;

    // http://localhost:8080/oauth2/authorization/google 테스트
    // OAuth2AuthenticationToken [Principal=Name: [106348243718351253046], Granted Authorities: [[OAUTH2_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]], User Attributes: [{sub=106348243718351253046, name=최동진, given_name=동진, family_name=최, picture=https://lh3.googleusercontent.com/a/ACg8ocKG0MI8EljK9Cta0LRx_zTBp00aHhl9REjqsUYo1weGE7uLLA=s96-c, email=choidj10@gmail.com, email_verified=true}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=AB6E37BCBC9E6156AACDB4BF0A51912B], Granted Authorities=[OAUTH2_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]]
    // http://localhost:8080/oauth2/authorization/kakao 테스트
    
    // [2] onAuthenticationSuccess 메소드 구현
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 타사(oauth2 : 구글/카카오/네이버/페이스북/깃허브/애플 등) 로그인 성공 이후 로직 커스텀 (성공 시만 커스텀하면 됨. 실패는 타사에서 알아 하시겄지)
        // [3] 로그인 성공한 회원의 타사 발급한 토큰 확인 , OAuth2AuthenticationToken 타사의 회사명
        System.out.println( authentication ); // authentication 인증 ( 토큰 , 개인정보 등 )
        // 3-1 : 의존성 추가 : implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
        // 3-2 : 로그인 성공한 토큰 확인 , OAuth2AuthenticationToken , 타사 회사명
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        System.out.println("authToken = " + authToken);
        // 3-3 : 로그인 성공한 회원의 동의항목(정보)
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User = " + oAuth2User);
        // [4] google / kakao 인지 식별 , 서로 다른 회사별 동의항목(다르다)
        String provider = authToken.getAuthorizedClientRegistrationId(); // 등록된 공급자 ID : google , kakao , 등등
        System.out.println("provider = " + provider);
        String uid = null ; String name = null; // 동의항목 정보
        if ( provider.equals("google")) {
            uid = oAuth2User.getAttribute("email"); // oAuth2User.getAttribute("동의항목명")
            name = oAuth2User.getAttribute("name");
        } else if ( provider.equals("kakao")) {
            // 카카오의 동의항목(정보) profile_nickname
            Map<String , Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String , Object> profile = (Map<String, Object>) kakaoAccount.get("profile"); // 테스트 단계에서는 profile만
            uid = (String) profile.get("nickname"); // 계정 ID는 동의항목 권한이 없으므로 임의처리
            name = (String) profile.get("nickname");
        }

        // [7] oauth2 정보를 DB 저장
        userService.oauth2UserSignup( uid , name );

        // [5] 자사(우리)의 로그인 방식 통합 , 권한은 USER 정의
        Cookie cookie = new Cookie("loginUser" , jwtService.createToken(uid, "USER"));
        cookie.setHttpOnly( true );
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge( 60 * 60 );
        response.addCookie( cookie );

        // [6] 로그인 성공시 어디로 이동할지 ( 프론트엔드 루트 )
        response.sendRedirect("http://localhost:5173/"); // 리액트 서버
        // response.sendRedirect("/"); // 자바서버 메인 경로 localhost:8080

    }
}

// 1. 어느 타사의 로그인 성공인지 확인
// 2. 로그인 성공한 동의항목 (정보) 가져오기 , 개인정보(아이디 , 회원명 , 주소, 전화번호)
// 3. 자사(우리) 서버와 타사(구글 등) 서버 통합 로그인( web2 : 토큰/쿠키 vs 세션 )
// 4. 자사 서버와 타사 서버 통합 DB (최초 로그인이면 DB 저장 , 아니면 DB 처리 없음)