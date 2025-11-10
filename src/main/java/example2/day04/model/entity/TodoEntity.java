package example2.day04.model.entity;

import example2.day04.model.dto.TodoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JPA 해당 엔티티를 테이블처럼 사용
@Table ( name = "todo" )
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity extends BaseTime {   // + BaseTime : 엔티티 생성/수정 날짜 상속
    
    // 1. 테이블 설계

    @Id // PK
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int id;             // PK

    @Column( nullable = false , length = 100 )  // + 테이블 속성 옵션
    private String title;       // 제목
    @Column( columnDefinition = "longtext" ) // + 테이블 속성 옵션
    private String content;     // 내용
    @Column( columnDefinition = "boolean default false" )
    private boolean done;       // 실행(체크)여부
    
    // 2. 참조 관계 설계
    
    // 3. Entity -> Dto 변환 : R
    public TodoDto toDto () {
        return TodoDto.builder()
                .id( this.id )
                .title( this.title )
                .content( this.content )
                .done( this.done )
                .create_date( this.getCreateDate().toString() )
                .update_date( this.getUpdateDate().toString() )
                .build();
    }

}
