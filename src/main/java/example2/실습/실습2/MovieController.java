package example2.실습.실습2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {

    /*
    [조건 6] 컨트롤러 설계 : REST API 형식의 컨트롤러 클래스를 작성한다.
    - /api/movie 경로를 기준으로 CRUD 요청을 처리할 것.
     */

    // [*] DI
    private final MovieService movieService;

    // [1] 등록
    // URL : http://localhost:8080/api/movie
    // BODY : { "title" : "노인을위한나라는없다" , "director" : "코엔형제" , "releaseDate" : "2013-04-15" , "rating" : "9.3" }
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieDto movieDto) {
        return  ResponseEntity.ok(movieService.addMovie(movieDto));
    }

    // [2] 전체조회
    // URL : http://localhost:8080/api/movie
    @GetMapping
    public ResponseEntity<?> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // [3] 개별조회
    // URL : http://localhost:8080/api/movie/find?movieId=1
    @GetMapping("/find")
    public ResponseEntity<?> getMovie(@RequestParam int movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    // [4] 수정
    // URL : http://localhost:8080/api/movie
    // BODY : { "title" : "가디언즈오브갤럭시Vol.1" , "director" : "그새끼" , "releaseDate" : "2017-10-24" , "rating" : "8.8"  , "movieId" : "1" }
    @PutMapping
    public ResponseEntity<?> updateMovie(@RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieService.updateMovie(movieDto));
    }

    // [5] 삭제
    // URL : http://localhost:8080/api/movie?movieId=1
    @DeleteMapping
    public ResponseEntity<?> deleteMovie(@RequestParam int movieId) {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }


}
