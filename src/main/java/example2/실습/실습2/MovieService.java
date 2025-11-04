package example2.실습.실습2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    /*
    [조건 5] 서비스 구현 : Service 클래스를 작성하여 아래 기능을 구현한다.
    기능 설명
    영화 등록       새로운 영화 정보를 입력받아 DB에 저장
    영화 전체 조회   모든 영화 목록을 조회
    영화 개별 조회   영화번호(movieId)를 기준으로 특정 영화 상세 정보 조회
    특정 영화 수정   영화번호(movieId)를 기준으로 해당 영화 정보 수정, @Transactional의 역할을 서술한다.
    특정 영화 삭제   영화번호(movieId)를 기준으로 해당 영화 삭제
     */

    // [*] DI
    private final MovieRepository movieRepository;

    // [1] 영화 등록
    public MovieDto addMovie(MovieDto movieDto) { // 1. 저장할 dto 매개변수로 받기
        // 2. dto를 entity로 변환
        MovieEntity movieEntity = movieDto.toEntity();
        // 3. .save() 이용한 엔티티 영속화
        MovieEntity savedEntity = movieRepository.save(movieEntity);
        // 4. PK 생성 시, 생성된 엔티티를 dto롤 변환 및 반환
        if ( savedEntity.getMovieId() >= 0) {
            return savedEntity.toDto();
        }
        // PK 없으면 dto 반환
        return movieDto;
    }
    
    // [2] 영화 전체 조회
    public List<MovieDto> getAllMovies() {
        // 1. 모든 엔티티 조회
        List<MovieEntity> movieEntityList =  movieRepository.findAll();
        // 2. 스트림으로 엔티티를 DTO로 변환
        List<MovieDto> movieDtoList = movieEntityList
                .stream().map( MovieEntity :: toDto )
                .collect(Collectors.toList());
        // 3. 반환
        return movieDtoList;
    }
    
    // [3] 영화 개별 조회
    public MovieDto getMovie(int movieId) {
        // 1. pk로 엔티티 조회
        Optional<MovieEntity> optional = movieRepository.findById(movieId);
        // 2. 엔티티 존재 여부 확인
        if (optional.isPresent()) {
            MovieEntity movieEntity = optional.get();
            return movieEntity.toDto();
        }
        // 3. 엔티티 없으면 null 처리
        return null;
    }
    
    // [4] 특정 영화 수정
    public MovieDto updateMovie(MovieDto movieDto) {
        // 1. pk로 엔티티 조회
        Optional<MovieEntity> optional = movieRepository.findById(movieDto.getMovieId());
        // 2. 엔티티 존재 여부 확인 후 있으면 수정
        if (optional.isPresent()) {
            MovieEntity movieEntity = optional.get(); // 엔티티 꺼내기
            movieEntity.setTitle(movieDto.getTitle());
            movieEntity.setDirector(movieDto.getDirector());
            movieEntity.setReleaseDate(movieDto.getReleaseDate());
            movieEntity.setRating(movieDto.getRating());
            // commit되면 자동으로 수정날짜는 변경됨
            return movieEntity.toDto(); // 수정된 엔티티를 dto로 변환 및 반환
        }
        // 3. 없으면 dto 반환
        return movieDto;
    }
    
    // [5] 특정 영화 삭제
    public boolean deleteMovie(int movieId) {
        // 1. pk로 엔티티 조회
        if( movieRepository.existsById( movieId )) { // .existsById( pk값 ) : pk값이 존재하면 true 없으면 false
            movieRepository.deleteById( movieId ); // 삭제 처리
            return true;
        }
        return false;
    }

}
