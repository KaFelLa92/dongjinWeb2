package example2.day04.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import example2.day04.model.entity.TodoEntity;

@Repository
public interface TodoRepository extends
        JpaRepository<TodoEntity , Integer> {
    // JPA Repository
    // 1. save() 2. findById() 3. findAll() 4.deleteById() 등등 미리 만들어진 CRD 제공
    
    // 2. 쿼리 메소드
    // * SQL 문장을 직접 작성하지 않고 메소드 이름으로 쿼리 생성 < 카멜표기법 >
    // * 주의! 카멜 틀리거나 한글자라도 오타나면 스프링 자체가 안 켜짐
    // 2-1 : findByTitle( String title ) <---> select * from todo where title = 매개변수
    List<TodoEntity> findByTitle( String title ); // findBy필드명( String title ) ,
    // 2-2 : findBy필드명and(or)필드명 <---> select * from todo where title = 매개변수 and content = 매개변수
    List<TodoEntity> findByTitleAndContent( String title, String content );
    // 2-3 : findByTitleContaining <---> select * from where title like %매개변수%;
    List<TodoEntity> findByTitleContaining( String keyword );
    // 2-5 : Pageable : import org.springframework.data.domain.Pageable; 검색에 페이징 붙여서 제공
    Page<TodoEntity> findByTitleContaining(String keyword , Pageable pageable);

    // 3. 네이티브 쿼리 메소드
    // * SQL 문장을 직접 작성하여 실행한다. , :(콜론)매개변수명 이용하여 매개변수값 대입한다.
    // 3-1 :
    // @Query( value = "SQL작성" , nativaQuery = true ) 추상메소드 주입한다.
    @Query( value = "select * from todo where title = :title " , nativeQuery = true )
    List<TodoEntity> query1 (String title);
    // 3-2 :
    @Query( value = "select * from todo where title = :title and content = :content " , nativeQuery = true)
    List<TodoEntity> query2 (String title, String content);
    // 3-3
    @Query( value = "select * from todo where title like %:keyword% " , nativeQuery = true ) // #{keyword} ---> :keyword
    List<TodoEntity> query3 (String keyword);

}
