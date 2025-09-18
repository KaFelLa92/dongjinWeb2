package example.day08.service;

import example.day08.model.dto.MemberDto;
import example.day08.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    // DI
    private final MemberMapper memberMapper;
    
    // [1] 등록
    public int memberAdd(MemberDto memberDto){
        return memberMapper.memberAdd(memberDto);
    }
    
    // [2] 전체조회
    public List<MemberDto> memberPrint(){
        return memberMapper.memberPrint();
    }
    
    // [3] 삭제
    public int memberDelete(int mno){
        return memberMapper.memberDelete(mno);
    }

}
