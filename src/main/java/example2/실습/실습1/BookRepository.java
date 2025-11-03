package example2.실습.실습1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity , Integer> {
    /*
    [조건 2] 리포지토리 구현 : 도서 엔티티를 관리하는 리포지토리 인터페이스를 설계한다.
    JpaRepository를 상속받아 기본 CRUD 메서드를 활용할 수 있도록 구성한다.
     */
}
