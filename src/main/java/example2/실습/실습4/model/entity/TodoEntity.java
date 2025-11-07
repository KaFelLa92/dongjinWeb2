package example2.실습.실습4.model.entity;

import example2.실습.실습4.model.dto.TodoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "todo")
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity extends BaseTime {
    /*
    [조건1] 엔티티 설계
    - Todo : 기본적인 할 일 정보를 저장 , id(pk/auto_increment) , title, content
    - 데이터베이스 테이블명은 todo 로 지정
    - BaseTime : 생성일(createdAt), 수정일(updatedAt)을 자동 저장
    - toDto() 포함
     */

    @Id // PK
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increament
    private int id;

    @Column( columnDefinition = "varchar(20) default '제목없음'" )
    private String title;

    @Column( columnDefinition = "varchar(255) default '냉무'" )
    private String content;

    // 엔티티 -> DTO / 서비스 -> 컨트롤러
    public TodoDto toDto(){
        return TodoDto.builder()
                .id( this.id )
                .title( this.title )
                .content( this.content )
                .created_at( this.getCreaedAt().toString() )
                .updated_at( this.getUpdatedAt().toString() )
                .build();
    }

} // class end
