package web2.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import web2.model.dto.UserDto;

@Mapper // 빈 등록
public interface UserMapper {

    // 1. 회원가입 (pk반환)
    @Insert("insert into users( uid, upwd , uname, uphone, urole , udate) values( #{uid}, #{upwd} , #{uname} , #{uphone}, #{urole} , #{udate} )")
    @Options(useGeneratedKeys = true , keyProperty = "uno") // insert 성공시 매개변수에 생성된 PK값 넣기
    int signup(UserDto userDto);

    // 2. 로그인 BCrypt에서 쓰려면 수정해야함
    // @Select("select * from users where uid = #{uid} and upwd = #{upwd} ")
    // UserDto login(UserDto userDto);

    @Select("select * from users where uid = #{uid} ") // 아이디로 계정 정보 조회
    UserDto login (String uid);

    // 3. 패스워드를 제외한 아이디로 계정 정보 조회
    @Select("select uno , uid, uname, uphone , urole, udate from users where uid = #{uid} ")
    UserDto myInfo (String uid);

}
