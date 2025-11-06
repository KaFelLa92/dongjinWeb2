package example2.day03._연관관계;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "ecategory" )
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id // PK
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int cno;
    private String cname;
    // ** 양방향 연결 **
    // 상위 엔티티가 하위 엔티티 참조관계
    @OneToMany( mappedBy = "categoryEntity" ) // 1:M , 하나PK가 다수FK에게
     // JPA 양방향 사용하고, DB에서는 양방향 하지 않는다.
    @ToString.Exclude // 순환참조 방지
    @Builder.Default // 빌더패턴 사용시 초기값 사용
    private List<BoardEntity>
            boardEntityList = new ArrayList<>();


}
