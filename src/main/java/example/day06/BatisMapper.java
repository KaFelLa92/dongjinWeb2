package example.day06;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper // 해당 인터페이스를 마이바티스(스프링 컨테이너)에 등록, DAO 역할 대신
public interface BatisMapper {

    // 1. 학생 등록 : ? 대신에 #{매개변수} 들어감
    @Insert("insert into student (name, kor, math) values (#{name} , #{kor} , #{math}) ")
    int save( StudentDto studentDto); // 추상메소드
        // int : insert된 레코드 수를 반환 , 1 : 성공 , 0 실패

    // 2. 전체 학생 조회
    @Select("select * from student")
    List< StudentDto > findAll();
    
    // 3. 개별 학생 조회 (Map 테스트)
    @Select("select * from student where sno = #{sno}")
    Map< String , Object > find(int sno);
    
    // 4. 개별 학생 삭제
    @Delete("delete from student where sno = #{sno}")
    int delete( int sno ); // 추상메소드

    // 5. 개별 학생 수정
    @Update("update student set name = #{name} , kor = #{kor} , math = #{math} where sno = #{sno}")
    int update( StudentDto studentDto );


} // inter end
