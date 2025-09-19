package eval6.service;

import eval6.model.dto.AgoraDto;
import eval6.model.mapper.AgoraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgoraService {
    // DI
    private final AgoraMapper agoraMapper;

    // [4] 토론 글 작성
    public int addAgora(AgoraDto agoraDto){
        return agoraMapper.addAgora(agoraDto);
    }

    // [5] 토론 글 삭제 (비번 기반)
    public int deleteAgora(String apwd, int ano){
        return agoraMapper.deleteAgora(apwd, ano);
    }

    // [6] 영화별 토론 전체 조회
    public List<AgoraDto> getAllAgoraSameCine(int cno){
        return agoraMapper.getAllAgoraSameCine(cno);
    }

    // [*] 토론 전체 조회
    public List<AgoraDto> getAllAgora(){
        return agoraMapper.getAllAgora();
    }
}
