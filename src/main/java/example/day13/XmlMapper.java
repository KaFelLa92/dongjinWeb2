package example.day13;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XmlMapper {

    // [1] 등록
    // MyBatis에서 SQL 매핑하는 방법
    // 방법1 : 추상메소드 위에 @Insert("SQL")
    // 방법2 : 추상메소드를 매핑하는 XML파일에서 SQL 작성
    // @Insert("insert into student(name, kor, math) values(#{name}, #{kor}, #{math}; ")
    // @Options(useGeneratedKeys = true , keyProperty = "sno") // autoincrement key(PK값) 반환
    // 어노테이션 vs Xml 하나만 해라
    int save (StudentDto dto);

    // [2] 전체조회
    List<StudentDto> findAll();

    // [3] 개별조회
    StudentDto find(int sno);

    // [4] 개별삭제
    int delete(int sno);

    // [5] 개별수정
    int update(StudentDto dto);

    // 동적쿼리 , 일반SQL 코드를 프로그래밍 SQL로 변경 : <if> <forEach> 등 사용 가능
    // [6] 특정한 국어 점수 이상의 학생 조회
    // 방법1 : @어노테이션( """<script> XML형식의 SQL </script> """ )
    @Select("""
            <script>
                select * from student where 1=1
                <if test="kor != null">
                    and kor >= #{kor}
                </if>
            </script>
            """)
    // """ """ : java15 이상부터 """ 템플릿 지원 , +연산자처럼 문자열 연결
    // where 1=1 : 무조건 true 만들기 위한 강제 참 true
        // 생략시 , select * from student where true and kor >= 90으로 문제발생한다.
        // 대체 : <where> 마크업 제공한다.
    // <if test="조건식"> 참일경우 SQL </if>
    List<StudentDto> query1(int kor);

    // 방법2 : XML
    List<StudentDto> query2(int kor);

    // [7] 이름(포함된) 또는 수학점수(이상)로 검색
    StudentDto query3(String name , int math);

    // [8] 여러개 학생 등록
    int saveAll(List<StudentDto> dtos);


} // inter end
