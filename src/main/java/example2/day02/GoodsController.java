package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {

    // [*] DI
    private final GoodsService goodsService;

    // [1] 등록
    // URL : http://localhost:8080/api/goods
    // BODY : { "gname" : "코카콜라" , "gprice" : "1500" , "gdesc" : "근본과전통이있는탄산음료" }
    @PostMapping
    public ResponseEntity<?> goodsSave (@RequestBody GoodsDto goodsDto) {
        return ResponseEntity.ok(goodsService.goodsSave(goodsDto));
    }

    // [2] 전체조회
    // URL : http://localhost:8080/api/goods
    @GetMapping
    public ResponseEntity<?> goodsFindAll () {
        return ResponseEntity.ok(goodsService.goodsFindAll());
    }

    // [3] 개별조회
    // URL : http://localhost:8080/api/goods/find?gno=1
    @GetMapping("/find")
    public ResponseEntity<?> goodsGet( @RequestParam int gno) {
        return ResponseEntity.ok( goodsService.goodsFindById(gno));
    }

    // [4] 수정
    // URL : http://localhost:8080/api/goods
    // BODY : { "gname" : "환타" , "gprice" : "1700" , "gdesc" : "과즙형탄산음료" , "gno" : "1" }
    @PutMapping
    public ResponseEntity<?> goodsUpdate (@RequestBody GoodsDto goodsDto) {
        return ResponseEntity.ok( goodsService.goodsUpdate(goodsDto));
    }

    // [5] 삭제
    // URL : http://localhost:8080/api/goods?gno=1
    @DeleteMapping
    public ResponseEntity<?> goodsDelete (@RequestParam int gno) {
        return ResponseEntity.ok( goodsService.goodsDelete(gno));
    }

}
