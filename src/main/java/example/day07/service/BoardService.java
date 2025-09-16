package example.day07.service;

import example.day07.model.dto.BoardDto;
import example.day07.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    // DI
    private final BoardMapper boardMapper;

    // [1] 등록
    public int boardWrite (BoardDto boardDto){
        // 리턴
        return boardMapper.boardWrite(boardDto);
    }

    // [2] 전체조회
    public List<BoardDto> boardPrint(){
        return boardMapper.boardPrint();
    }

    // [3] 개별조회
    public BoardDto boardFind(int bno){
        return boardMapper.boardFind(bno);
    }

    // [4] 개별수정
    public int boardUpdate(BoardDto boardDto){
        return boardMapper.boardUpdate(boardDto);
    }

    // [5] 개별삭제
    public int boardDelete(int bno){
        return boardMapper.boardDelete(bno);
    }
}
