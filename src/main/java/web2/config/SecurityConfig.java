package web2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 설정 빈 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;                  // 내가 만든 토큰을 시큐리티 토큰 방식으로 통합한 클래스
    private final Oauth2SuccessHandler oauth2SuccessHandler;    // Oauth2 인증으로 소셜 로그인

    // [*] 콘솔에서 시큐리티 패스워드 확인하기
    // Using generated security password: 3b95894a-914a-428c-8803-e45744abdfcc
    // login 창에서 사용자 이름 : user , 비밀번호 : 시큐리티 패스워드 넣기

    // 스프링 시큐리티 라이브러리 커스텀
    // 시큐리티(보안) 필터(검증/확인) 만들기
    // 미리 만들어진 필터들이 다수... 그런 필터들을 커스텀/제외/끄기
    
    // [*] HTTP 관련 필터들을 커스텀 , HTTP는 요청과 응답을 처리하는 웹 아키텍처
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                // [1] HTTP 요청에 따른 권한 커스텀
                // .authorizeHttpRequests( auth -> auth.requestMatchers("경로").권한 );
                // .permitAll() : 모든 권한 허용
                // .hasRole("권한명") , .hasAnyRole("권한명", "권한명");
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/info").hasAnyRole("USER" , "ADMIN") // 2개 이상 가능. 권한명은 대문자
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // admin 관련 controller는 "ADMIN" 권한만 요청 가능
                        .requestMatchers("/**").permitAll() );              // 모든 권한 허용은 항상 최하단에 정의한다.

                // [2] HTTP 요청에 csrf( 요청 간의 해킹 공격 ) POST,PUT 자동 차단 커스텀
                // http.csrf( csrf -> csrf.ignoringRequestMatchers("csrf제외할경로") );
        http.csrf( csrf -> csrf.disable()); // .disable() : 개발단계 권장 .csrf 사용 안함
        
                // [3] 시큐리티 내부에서 사용되는 (세션)토큰 , UsernamePasswordAuthenticationToken
                // web2 실습에서는 쿠키 기반의 토큰 구현 중 = 시큐리티가 제공하는 세션 사용 안함
                // [3-1] 시큐리티 세션 끄기
        http.sessionManagement(session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
                // [3-2] UsernamePasswordAuthenticationToken을 개발자가 만든 토큰으로 대체하기
                // http.addFilterBefore( 내가만든토큰객체필터 , UsernamePasswordAuthenticationFilter.class );
        http.addFilterBefore( jwtAuthFilter , UsernamePasswordAuthenticationFilter.class );

                // [4] Oauth2 로그인 필터 사용 설정
                // http.oauth2Login( 매개변수 -> 매개변수.successHandler( 로그인성공시클래스이동 ) );
        http.oauth2Login(o -> o
                .loginPage("/oauth2/authorization/google")  // 타사 로그인 페이지로 이동
                .successHandler(oauth2SuccessHandler));      // 타사 로그인 페이지에서 로그인 성공시 반환되는 클래스 정의

                // [5] 시큐리티 CORS 정책을 필터링 기본(우리가 설정한 CorsConfig 클래스) 설정하기
        http.cors(Customizer.withDefaults());



        return http.build();    // 커스텀 완료된 객체 반환
    }



}
