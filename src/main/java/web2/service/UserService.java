package web2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web2.model.dto.UserDto;
import web2.model.mapper.UserMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    // [*] DI
    private final UserMapper userMapper;
    // 1-2 : 비크립트 라이브러리 객체 주입
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    
    // 1. 회원가입
    public int signup(UserDto userDto){
        // 1-3 : 회원가입 하기 전에 비크립트를 이용한 비밀번호 암호화(사람이 이해하기 어려운 데이터로 변경)하기
        // String 암호화된데이터 = bcrypt.encode( 암호화할데이터 );
        userDto.setUpwd( bcrypt.encode(userDto.getUpwd() ) ); // 1234 ---> xxxxx 암호화는 되지만 복호화 안 됨
        userMapper.signup( userDto ); // mapper 이용한 sql 처리
        if( userDto.getUno() > 0){ // 회원가입 SQL 처리 성공
            return userDto.getUno(); // 회원번호(PK) 반환
        } else {
            return 0; // 회원가입 실패를 0으로 가정한다.
        }
    } // method end

    // 2. 로그인 : 암호문을 해독하여 평문을 비교하는 방식이 아닌, 비교할 대상을 암호문으로 변경하여 암호문 비교
    public UserDto login(UserDto userDto){
        // 2-1. : 현재 로그인에서 입력받은 아이디 계정이 있는지 확인
        UserDto result = userMapper.login(userDto.getUid());
        if ( result == null ){
            return null;
        }
        // 2-2 : 만약 입력받은 아이디 계정이 존재하면, 입력받은 비밀번호와 암호화된 비밀번호 비교
        // 평문비교가 아니므로 == .equlas 불가능하다.
        // 암호문 비교 방식인 .matches( 비교할비밀번호평문 , 암호문 )
        System.out.println("[평문 : 로그인시 입력받은 비밀번호] = " + userDto.getUpwd());
        System.out.println("[암호문 : 회원가입시 입력받은 비밀번호 ] = " + result.getUpwd());
        boolean result2 = bcrypt.matches(userDto.getUpwd(), result.getUpwd());
        // bcrypt.matches(1234, $2a$10$Nor.eIziSW0m0umwOTZeu.wtupxbcRSgh2uzsnNVdee01ZGK3odxy);
        if (result2 == true ) { // 비밀번호 일치하면 로그인 성공
            result.setUpwd(null); // 비밀번호 성공시 반환되는 계정에는 비밀번호 제외
            return result;
        } else {
            return null;
        }
    } // method end

    /*
    
        회원의 비밀번호를 암호화
        1234(평문) ---> 나만의계산식 ---> 42348435354187(암호문)
        * 평문 : 본래의 데이터 , 암호문 : 암호화된 데이터
        * 암호화 : 사람이 이해할 수 없는 데이터로 변경
        * 복호화 : 암호화된 데이터를 다시 평문으로 변경
        나만의계산식 : 암호화 알고리즘(순서도) ,
        비밀번호에서 사용되는 대표 : 비크립트(복호화 불가능)

    */

    // 3. 내 정보 조회
    public UserDto myInfo(String uid){
        UserDto result = userMapper.myInfo(uid);
        return result;
    }

    // 4. [oauth2] 회원가입 구성
    public UserDto oauth2UserSignup(String uid, String name){
        // 4-1 : 기존 회원인지 검사
        UserDto userDto = userMapper.login( uid );
        if ( userDto == null ) { // 기존 회원정보 없음
            UserDto oauthUser = new UserDto();
            oauthUser.setUid( uid );
            oauthUser.setUpwd( null );      // 타사이므로 없음 , 패스워드가 없는 회원은 oauth2 로그인
            oauthUser.setUname( name );
            oauthUser.setUrole( "USER" );   // 추후 일반유저와 OAUTH 유저 권한 구분 가능 "USER" , "ADMIN" , "OAUTH"
            userMapper.signup( oauthUser );
            return oauthUser;
        }
        return null;
    }



}
