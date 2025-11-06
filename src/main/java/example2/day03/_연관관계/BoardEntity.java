package example2.day03._연관관계;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "eboard" )
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    @Id // PK
    @GeneratedValue( strategy = GenerationType.IDENTITY )   // auto_increment
    private int bno;
    private String btitle;
    private String bcontent;
    // ** 단방향 연결 **
    // 하위 엔티티가 상위 엔티티 참조관계
    @ManyToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY) // M:1 , 다수가 하나에게
    // CasCadeType.ALL : 같이 수정되고 같이 삭제됨
    @JoinColumn( name = "cno" ) // FK 필드명 (+PK필드명과 동일 권장)
    private CategoryEntity categoryEntity;

    // 양방향 연결
    @OneToMany( mappedBy = "boardEntity" )
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity>
            replyEntityList = new ArrayList<>();


}

/*

    [ 영속성 ] 자바 데이터(객체)와 데이터베이스 데이터(테이블/레코드)를 매핑(연결)
        - 즉] '영속 중'이라면 자바 객체 수정 시 DB 데이터 수정 된다.
        - 즉] '영속 중' 아니라면 자바 객체 수정해도 DB 데이터 그대로.
    PK - FK 영속성 제약조건 옵션
    [ cascade ]
        cascade = CascadeType.ALL : 부모가 삭제/수정/저장/REFRESH/DETACH 되면 자식도 같이 삭제/수정/저장/REFRESH/DETACH 된다.
        cascade = CascadeType.PERSIST : 부모가 *저장* 되면 자식도 같이 *저장* 된다.
        cascade = CascadeType.MERGE : 부모가 *수정* 되면 자식도 같이 *수정* 된다.
        cascade = CascadeType.REMOVE : 부모가 *삭제* 되면 자식도 같이 *삭제* 된다.
        cascade = CascadeType.REFRESH : 부모가 재호출(갱신) 되면 자식도 같이 재호출(갱신) 된다.
        cascade = CascadeType.DETACH : 부모가 영속 해제되면 자식도 영속 해제
    [ fetch ] 부모[PK]와 자식[FK] 관계에서 엔티티를 조회하는 방법
        fetch = FetchType.EAGER : 해당 엔티티를 조회하면 참조 엔티티도 즉시 조회한다.
            - 특징 : 기본값 , 초기 로딩 느리다 , *불필요한 엔티티 정보*가 있을 경우 기능 저하
        fetch = FetchType.LAZY : 해당 엔티티를 조회하면 참조 엔티티를 조회하지 않는다.
            - 특징 : 초기 로딩 빠르다.
            - 해당엔티티.getXXX() 하는 순간 참조하는 엔티티 조회 ( 지연 로딩 )
    * 동일한 SELECT(조회)된 결과는 MyBatis와 JPA는 자동 1차 캐싱(기록)

 */
