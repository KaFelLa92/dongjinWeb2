package example2.day02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 엔티티는 서비스에서만 사용
// 컨트롤러는 DTO 그대로 써야함
// JPA + MyBatis 혼합해서 쓰는 경우도 있음
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    // 데이터베이스/엔티티 필드/속성 기반으로 구성
    private int gno;
    private int gprice;
    private String create_date;
    private String update_date;
    private String gname;
    private String gdesc;
    // DTO --> Entity
    // 컨트롤러 --> 서비스 : C(등록) U(수정)
    public GoodsEntity toEntity() {
        return GoodsEntity.builder()
                .gno( this.gno )
                .gname( this.gname )
                .gprice( this.gprice )
                .gdesc( this.gdesc )
             // .date 자동이므로 제외
                .build();
    }


} // class end
