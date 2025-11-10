package example2.day04.model.dto;

import example2.day04.model.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    // dto = 데이터 이동 객체
    // 기능 구현에 필요한 목적에 따른 이동할 데이터 구성
    // 실무 : 1) 테이블유사 2) 기능/상황 별 <---> Map
    private int id;                 // R U
    private String title;           // C R U
    private String content;         // C R U
    private boolean done;           // C R U
    private String create_date;     // R
    private String update_date;     // R

    // Dto -> Entity 변환 : C (생성/수정시간은 auto라서 필요없음)
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .id( this.id )
                .title( this.title )
                .content( this.content )
                .done( this.done )
                .build();
    }

} // class end
