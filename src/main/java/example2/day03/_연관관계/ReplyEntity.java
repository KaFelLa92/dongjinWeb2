package example2.day03._연관관계;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "ereply" )
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rno;            // 댓글번호
    private String rcontent;    // 댓글내용
    // 단뱡향 연결
    @ManyToOne( cascade = CascadeType.ALL , fetch =  FetchType.LAZY )
    @JoinColumn( name = "bno" ) // FK 필드명 (PK필드명과 동일하게)
    private BoardEntity boardEntity;
}


// 카테고리 <-- 게시물 <-- 댓글