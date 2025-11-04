package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {

    // [*] DI
    private final GoodsRepository goodsRepository;

    // [1] 등록
    public GoodsDto goodsSave(GoodsDto goodsDto) { // 1. 저장할 dto를 매개변수로 받기
        // 2. 저장할 dto를 entity 변환
        GoodsEntity entity = goodsDto.toEntity();
        // 3. .save() 이용한 엔티티 영속화
        GoodsEntity savedEntity = goodsRepository.save( entity ); 
        // 4. 만약에 PK가 생성되었으면 생성된 엔티티를 dto로 변환하여 반환
        if( savedEntity.getGno() >= 0) { 
            return savedEntity.toDto(); 
        }
        return goodsDto;
    }

    // [2] 전체조회
    public List<GoodsDto> goodsFindAll() {
        // 1. 모든 엔티티를 조회한다.
        List<GoodsEntity> goodsEntityList = goodsRepository.findAll( );
        // 2. 모든 엔티티를 dto로 변환
        // 방법1 :
//        List<GoodsDto> goodsDtoList = new ArrayList<>();
//        for (int i = 0; i < goodsEntityList.size(); i++) {
//            GoodsEntity entity = goodsEntityList.get(i);    // i번째 엔티티 꺼내기
//            goodsDtoList.add( entity.toDto());              // 엔티티를 dto 변환 후 리스트 저장
//        }
        // 방법2 : 스트림API
        List<GoodsDto> goodsDtoList = goodsEntityList
                .stream().map( GoodsEntity :: toDto )
                .collect(Collectors.toList());

        return goodsDtoList; // dto List 반환한다.
    }

    // [3] 개별조회
    public GoodsDto goodsFindById(int gno) {
        // 1. gno(pk)로 엔티티 조회
        Optional<GoodsEntity> optional = goodsRepository.findById( gno );
        // 2. 존재여부 확인
        if (optional.isPresent()) {
            GoodsEntity entity = optional.get();
            return entity.toDto(); // 엔티티를 dto로 반환
        }
        // 3. 없으면 null
        return null;
    }

    // [4] 수정
    public GoodsDto goodsUpdate (GoodsDto goodsDto) {
        // 1. gno(pk)로 엔티티 조회
        Optional<GoodsEntity> optional = goodsRepository.findById( goodsDto.getGno());
        // 2. 존재여부 확인 후 수정
        if (optional.isPresent()) {
            GoodsEntity entity = optional.get(); // 영속화된 엔티티 꺼내기
            entity.setGname(goodsDto.getGname());
            entity.setGprice(goodsDto.getGprice());
            entity.setGdesc(goodsDto.getGdesc());
            // commit되면 자동으로 수정날짜 (JPA Auditing) 자동 변경 : 변경된 값이 존재할 경우!
            return entity.toDto(); // 수정된 엔티티를 dto로 변환후 반환
        }
        // 3. 존재하지 않으면 dto 반환
        return goodsDto; 
    }

    // [5] 삭제
    public boolean goodsDelete(int gno) {
        // 1. gno(pk)로 엔티티 조회
        if(goodsRepository.existsById( gno )){  // .existsById( pk값 ) : pk값이 존재하면 true 없으면 false
            goodsRepository.deleteById( gno );  // 삭제 처리
            return true;
        }
        return false;
    }

}
