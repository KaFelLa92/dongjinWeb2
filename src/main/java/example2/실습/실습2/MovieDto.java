package example2.실습.실습2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    /*
    [조건 3] DTO 설계 : 영화 정보를 전송하기 위한 DTO 클래스를 생성한다.
    - DTO는 View와 Controller 간 데이터 전달용 객체이다.
    - 필드는 Entity와 동일하게 구성하되, 아래 메서드를 포함할 것.
    - public MovieEntity toEntity() : DTO → Entity 변환
     */
    // 엔티티 기반으로 멤버변수(필드) 설정
    private int movieId;
    private String title;
    private String director;
    private String releaseDate;
    private double rating;
    
    // dto에서 엔티티로 / 컨트롤러에서 서비스로
    public MovieEntity toEntity() {
        return MovieEntity.builder()
                .movieId( this.movieId )
                .title( this.title )
                .director( this.director )
                .releaseDate( this.releaseDate )
                .rating( this.rating )
                .build();
    }
} // class end
