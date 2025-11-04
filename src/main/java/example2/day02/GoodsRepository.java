package example2.day02;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 빈 등록
// 지정한 엔티티들을 조작하는 인터페이스
public interface GoodsRepository
        extends JpaRepository<GoodsEntity, Integer> {
    // extends JpaRepository <조작할엔티티명/엔티티PK자료형>


}
