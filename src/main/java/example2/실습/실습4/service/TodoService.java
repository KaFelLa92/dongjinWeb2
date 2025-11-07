package example2.실습.실습4.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import example2.실습.실습4.model.dto.TodoDto;
import example2.실습.실습4.model.entity.TodoEntity;
import example2.실습.실습4.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    /*
    [조건2] DTO / Repository / Service / Controller 설계
    - 등록 (createTodo), 전체조회 (getTodoList)
     */

    // [*] DI
    private final TodoRepository todoRepository;

    // [1] 등록
    public TodoDto createTodo(TodoDto todoDto) { // 1. 저장할 dto 매개변수 넣기
        // 2. dto -> entity로 변환
        TodoEntity todoEntity = todoDto.toEntity();
        // 3. .save() 이용한 엔티티 영속화
        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        // 4-1. 성공) PK 생성 시, 생성된 엔티티 -> dto 변환 및 반환
        if (savedTodoEntity.getId() >= 0 ){
            return savedTodoEntity.toDto();
        }
        // 4-2. 실패) PK 없으면 dto 반환
        return todoDto;
    }

    // [2] 전체조회
    public List<TodoDto> getTodoList() {
        // 1. 모든 엔티티 조회 및 스트림으로 엔티티 -> dto 변환
        List<TodoDto> todoDtoList = todoRepository.findAll()
                .stream().map( TodoEntity :: toDto )
                .collect(Collectors.toList());
        // 2. dto 배열 반환
        return todoDtoList;
    }

}
