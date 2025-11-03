package example2.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamService {

    // * 매퍼객체 --> 리포지토리객체
    private final ExamRepository examRepository;

    // [1] C , 등록
    public ExamEntity post( ExamEntity examEntity ) { // 1. 저장할 엔티티를 (매개변수) 받는다.
        // 2. .save() , 리포지토리의 저장 메소드 , 저장 성공 시 성공된 엔티티 반환
        ExamEntity saveEntity = examRepository.save(examEntity); // insert 자동 처리
        // 엔티티객체(레코드=행=dto1개) 엔티티(테이블)
        return saveEntity;
    }

    // [2] R , 전체조회
    public List<ExamEntity> get() {
        // 1. .findAll() , 리포지토리의 전체조회 메소드 , 모든 엔티티객체를 반환한다.
        List<ExamEntity> entityList  = examRepository.findAll();
        return entityList;
    }

    // [3-1] U , 특정한 엔티티 수정 , *비권장*
    public ExamEntity put(ExamEntity examEntity) {
        // 1. 수정할 엔티티 매개변수로 받는다. PK포함
        // 2. save( 수정할엔티티 )
            // 만약에 지정한엔티티에 PK가 없으면 *생성
            // 만약에 지정한엔티티에 PK가 존재하면 *수정
        ExamEntity updateEntity = examRepository.save(examEntity);
        return updateEntity;
    }

    // [3-2] U , 특정한 엔티티 수정 , *권장* 주의할점 : 엔티티를 setter하면 자동으로 DB도 변경된다.
    // setter는 서비스에서 처리하고 컨트롤러에서는 가능한 쓰지 말 것.
    @Transactional // 트랜잭션 : 여러개의 SQL에서 하나라도 실패하면 모두 실패하고 롤백시킴
    public ExamEntity put2 (ExamEntity examEntity) {
        // 1. 수정할 엔티티 조회한다. findAll(), findByID( pk번호 )
        Optional< ExamEntity > optional =
                examRepository.findById(examEntity.getCol1());
        // 2. Optional이란? 자바에서 자주 발생하는 nullPointer 예외를 감싼 포장 클래스
        // 즉] null값에 대한 안전하게 유효성 기능 제공한다.
        if( optional.isPresent()) {             // .isPresent() : 본문이 존재하는지 검사
            ExamEntity entity = optional.get(); // .get() : 만약에 결과에 entity 존재하면 entity 꺼내기
            entity.setCol2( examEntity.getCol2()); // setter 이용한 엔티티 값 수정
            entity.setCol3( examEntity.getCol3()); // setter 이용한 엔티티 값 수정
            return entity;
        }
        return examEntity;
    }

    // [4] D , 특정한 엔티티 삭제
    public boolean delete( int col1) {
        // 1. 삭제할 PK 번호 받기
        // 2. deleteById( pk번호 )
        examRepository.deleteById(col1);
        return true;
    }

} // class end
