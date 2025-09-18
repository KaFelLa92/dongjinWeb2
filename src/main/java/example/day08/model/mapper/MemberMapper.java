package example.day08.model.mapper;

import example.day08.model.dto.MemberDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
    // [1] 등록
    @Insert("insert into members (name, phone, age) values (#{name} , #{phone} , #{age})" )
    int memberAdd(MemberDto memberDto);

    // [2] 전체조회
    @Select("select * from members")
    List<MemberDto> memberPrint();

    // [3] 삭제
    @Delete("delete from members where mno = #{mno}")
    int memberDelete(int mno);
}
