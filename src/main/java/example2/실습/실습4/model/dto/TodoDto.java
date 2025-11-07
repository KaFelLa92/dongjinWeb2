package example2.실습.실습4.model.dto;

import example2.실습.실습4.model.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    /*
    [조건2] DTO / Repository / Service / Controller 설계
    - DTO는 Entity ↔ DTO 변환을 위한 toEntity( ) 포함
     */

    private int id;
    private String title;
    private String content;
    private String created_at;
    private String updated_at;

    // DTO -> entity / controller -> service
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .id( this.id )
                .title( this.title )
                .content( this.content )
                .build();
    }

} // class end
