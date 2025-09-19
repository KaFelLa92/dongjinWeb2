package eval6.model.mapper;

import eval6.model.dto.AgoraDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgoraMapper {
    // [4] 토론 글 작성
    @Insert("insert into agora (cno , acontent , apwd) values (#{cno} , #{acontent} , #{apwd})")
    @Options(useGeneratedKeys = true , keyProperty = "ano")
    int addAgora(AgoraDto agoraDto);

    // [5] 토론 글 삭제 (비번 기반)
    @Delete("delete from agora where apwd = #{apwd} and ano = #{ano}" )
    int deleteAgora(String apwd , int ano);

    // [6] 영화별 토론 전체 조회
    @Select("select * from agora where cno = #{cno} ")
    List<AgoraDto> getAllAgoraSameCine(int cno);

    // [*] 토론 전체 조회
    @Select("select * from agora")
    List<AgoraDto> getAllAgora();

}
