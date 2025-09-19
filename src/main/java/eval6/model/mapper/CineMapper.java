package eval6.model.mapper;

import eval6.model.dto.CineDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CineMapper {

    // [1] 영화 등록
    @Insert("insert into cine (ctitle , cdirector , cgenre , ccontent , cpwd) values (#{ctitle} , #{cdirector} , #{cgenre} , #{ccontent} , #{cpwd})")
    @Options(useGeneratedKeys = true , keyProperty = "cno")
    int addCine(CineDto cineDto);

    // [2] 영화 삭제
    @Delete("delete from cine where cpwd = #{cpwd} and cno = #{cno} ")
    int deleteCine(String cpwd , int cno);

    // [3] 영화 목록 조회
    @Select("select * from cine")
    List<CineDto> getAllCine();

}
