package example2.실습.실습4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import example2.실습.실습4.model.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity,Integer> {
    /*
    [조건2] DTO / Repository / Service / Controller 설계
    - Repository는 JpaRepository를 상속
     */
}
