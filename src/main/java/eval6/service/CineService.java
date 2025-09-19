package eval6.service;

import eval6.model.dto.CineDto;
import eval6.model.mapper.CineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CineService {
    // DI
    private final CineMapper cineMapper;
    // [1] 영화 등록
    public int addCine(CineDto cineDto){
        return cineMapper.addCine(cineDto);
    }
    
    // [2] 영화 삭제 (비번 기반)
    public int deleteCine(String cpwd , int cno){
        return cineMapper.deleteCine(cpwd, cno);
    }
    
    // [3] 영화 목록 조회
    public List<CineDto> getAllCine(){
        return cineMapper.getAllCine();
    }
}
