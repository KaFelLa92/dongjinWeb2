package example.day07.model.mapper;

import example.day07.model.dto.BoardDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    // [1] 등록
    @Insert("insert into board (bcontent , bwriter) values (#{bcontent} , #{bwriter}) ")
    int boardWrite(BoardDto boardDto);

    // [2] 전체조회
    @Select("select * from board")
    List< BoardDto > boardPrint();

    // [3] 개별조회
    @Select("select * from board where bno = #{bno}")
    BoardDto boardFind(int bno);

    // [4] 개별수정
    @Update("update board set bcontent = #{bcontent} , bwriter = #{bwriter} where bno = #{bno}")
    int boardUpdate(BoardDto boardDto);

    // [5] 개별삭제
    @Delete("delete from board where bno = #{bno}")
    int boardDelete(int bno);
}
