package example2.실습.실습2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 엔티티 테이블 매핑
@Table( name = "theater" ) // 테이블 이름 정의 : movie 이미 있어서 theater로
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    /*
    [조건 1] 엔티티 설계 : “영화(Movie)” 정보를 저장할 엔티티 클래스를 생성한다.
    - 각 필드의 역할과 데이터 타입을 적절히 설정한다.
    - @Entity, @Table, @Id, @GeneratedValue, @Column 등의 JPA 어노테이션을 활용한다.
    - 항목을 포함할 것: 영화번호(movieId), 영화제목(title), 감독(director), 개봉일(releaseDate), 평점(rating)
    - 공통 상속 엔티티(BaseTime)를 적용하여 createdAt, updatedAt 자동 기록 기능을 포함한다.
    - public MovieDto toDto( ) : Entity → DTO 변환
     */

    @Id // PK 필드 주입
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment 주입
    private int movieId;        // 영화번호

    @Column( columnDefinition = "varchar(100) default '어떤 영화' not null" )
    private String title;       // 영화제목

    @Column( columnDefinition = "varchar(100) default '익명의 감독' not null" )
    private String director;    // 감독

    @Column( columnDefinition = "datetime default now() not null" )
    private String releaseDate; // 개봉일

    @Column( columnDefinition = "double default 10.0 not null")
    private double rating;      // 평점

    // 엔티티에서 dto로 / 서비스에서 컨트롤러로
    public MovieDto toDto() {

        return MovieDto.builder()
                .movieId( this.movieId )
                .title( this.title )
                .director( this.director )
                .releaseDate( this.releaseDate )
                .rating( this.rating )

                .build();
    }

} // class end
