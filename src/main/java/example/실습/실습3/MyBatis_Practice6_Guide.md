안녕하세요! 실습 6번 진행 상황을 공유해주셔서 감사합니다. 지문을 해석하고 MyBatis 매퍼를 작성하는 과정에서 헷갈리는 부분이 있는 것은 당연합니다. 아주 잘하고 계십니다.

요청하신 대로, 작성하신 매퍼(Mapper) 코드를 검토하고 Service와 Controller 계층의 구현 가이드라인을 마크다운 파일로 정리해 드립니다.

### 실습 6번 요구사항 분석

먼저 지문을 다시 한번 정리해보겠습니다.

1.  **뷰(View) 생성 기능 2개 (DDL 이므로 `<update>` 태그 사용)**
    *   `detail_view`: `rentals`와 `books` 테이블을 JOIN하여 대출 상세 정보를 보여주는 뷰.
    *   `most_stock_view`: 재고가 많은 도서를 보여주는 뷰.
2.  **생성된 뷰(View) 조회 기능 2개 (`<select>` 태그 사용)**
    *   `detail_view`를 조회하는 기능.
    *   `most_stock_view`를 조회하는 기능.

이해하신 내용이 대체로 맞지만, '재고가 많은'의 기준이나 조회 방식 등 일부 SQL에서 개선할 부분이 보입니다. 아래에서 자세히 설명해 드릴게요.

---

## MyBatis Mapper 검토 및 수정 제안

작성하신 `BookMapper.xml`의 실습 6번 관련 부분을 중심으로 검토하고 수정안을 제안합니다.

### 1. 대출기록 상세 뷰 생성 (`createDetailView`)

**작성하신 코드:**
```xml
<update id="createDetailView" parameterType="example.실습.실습3.RentalsDto">
    create or replace view detail_view as select * from rentals r inner join books b on r.book_id = b.id
</update>
```
-   **잘한 점**: `CREATE VIEW`는 테이블 구조를 변경하는 DDL이므로 MyBatis에서 `<update>` 태그를 사용하는 것이 맞습니다. `CREATE OR REPLACE`를 사용하여 뷰가 이미 존재해도 에러 없이 덮어쓸 수 있게 한 점도 좋습니다.
-   **개선 제안**:
    1.  **`SELECT *` 사용 주의**: `rentals`와 `books` 테이블에 모두 `id`와 같이 이름이 같은 컬럼이 있다면, `SELECT *` 사용 시 컬럼명이 중복되어 문제를 일으킬 수 있습니다. **별칭(alias)**을 사용해 각 컬럼의 이름을 명확히 해주는 것이 안전합니다.
    2.  **불필요한 파라미터**: 뷰를 생성하는 SQL에는 파라미터가 사용되지 않으므로 `parameterType`은 제거해도 됩니다. Java 인터페이스의 메소드 시그니처도 `int createDetailView();` 와 같이 파라미터 없이 변경하는 것이 좋습니다.

**수정 제안:**
```xml
<!-- BookMapper.xml -->
<update id="createDetailView">
    CREATE OR REPLACE VIEW detail_view AS
    SELECT
        r.id AS rental_id,          -- 대출 기록 ID
        r.rental_date,              -- 대출일
        r.return_date,              -- 반납일
        r.member,                   -- 대출 회원
        b.id AS book_id,            -- 도서 ID
        b.title,                    -- 도서명
        b.stock                     -- (참고용) 현재 재고
    FROM rentals r
    INNER JOIN books b ON r.book_id = b.id
</update>

// BookMapper.java
int createDetailView(); // 파라미터 제거
```

### 2. 많은 재고를 가진 도서 조회 뷰 생성 (`createMostStockView`)

**작성하신 코드:**
```xml
<update id="createMostStockView" parameterType="example.실습.실습3.BooksDto">
    create or replace view most_stock as select * from books
    <where>
        <if test="stock != 0">
            and max(stock)
        </if>
    </where>
</update>
```
-   **검토 의견**: 이 부분은 지문의 '많은 재고'라는 모호한 표현 때문에 구현이 어려우셨을 것 같습니다. 현재 작성된 SQL은 `WHERE` 절에 집계 함수 `max()`를 잘못 사용하고 있어 문법 오류가 발생합니다.
-   **해석 및 수정 제안**: '많은 재고'는 **'평균 재고 수량보다 많은 재고를 가진'** 으로 해석하는 것이 가장 합리적입니다. 이 기준에 따라 서브쿼리를 사용하여 뷰를 만드는 것이 좋습니다. 이 경우, 동적 쿼리보다는 정적인 뷰를 만드는 것이 더 적합합니다.

**수정 제안:**
```xml
<!-- BookMapper.xml -->
<update id="createMostStockView">
    CREATE OR REPLACE VIEW most_stock_view AS
    SELECT *
    FROM books
    WHERE stock > (SELECT AVG(stock) FROM books)
</update>

// BookMapper.java
int createMostStockView(); // 파라미터 제거
```

### 3. 대출기록 상세 뷰 조회 (`getDetailView`)

**작성하신 코드:**
```xml
<select id="getDetailView" resultType="example.실습.실습3.RentalsDto" parameterType="int">
    select * from detail_view
    <where>
        <if test="id != 0">
            and id = #{rentId}
        </if>
    </where>
</select>
```
-   **검토 의견**:
    1.  **파라미터 불일치**: Java 인터페이스에서는 파라미터 이름이 `rentId`인데, XML의 `<if>`문에서는 `id`를 검사하고 있습니다. `test="rentId != 0"`으로 통일해야 합니다.
    2.  **결과 타입**: `resultType`을 `RentalsDto`로 지정했지만, `detail_view`는 `books` 테이블의 `title` 등 추가적인 컬럼을 가지고 있습니다. `RentalsDto`에 `title` 필드가 없다면 값을 제대로 받아오지 못합니다. 이럴 때는 `Map`으로 받거나, JOIN 결과를 담을 새로운 DTO를 만드는 것이 좋습니다.
-   **수정 제안**: 특정 `rental_id`로 조회하는 기능과 전체 목록을 조회하는 기능을 명확히 분리하는 것이 좋습니다. 여기서는 **전체 목록을 조회**하는 것으로 수정해 보겠습니다.

**수정 제안 (전체 목록 조회):**
```xml
<!-- BookMapper.xml -->
<select id="getDetailView" resultType="map">
    SELECT * FROM detail_view
</select>

// BookMapper.java
// JOIN 결과를 유연하게 받기 위해 List<Map<String, Object>> 사용
List<Map<String, Object>> getDetailView();
```

### 4. 많은 재고를 가진 도서 조회 뷰 조회 (`getMostStockView`)

**작성하신 코드:**
```xml
<select id="getMostStockView" resultType="example.실습.실습3.BooksDto" parameterType="int">
    select * from most_stock
</select>
```
-   **검토 의견**: Java 인터페이스는 `int bookId` 파라미터를 받지만, SQL 쿼리에서는 이 파라미터를 사용하지 않고 있습니다. 이는 기능의 의도와 구현이 일치하지 않는 상태입니다.
-   **수정 제안**: 이 기능은 '재고가 많은 도서'들의 **전체 목록**을 조회하는 것이므로, 파라미터 없이 `List<BooksDto>`를 반환하는 것이 자연스럽습니다.

**수정 제안:**
```xml
<!-- BookMapper.xml -->
<select id="getMostStockView" resultType="example.실습.실습3.BooksDto">
    SELECT * FROM most_stock_view
</select>

// BookMapper.java
List<BooksDto> getMostStockView(); // 파라미터 제거 및 반환타입 List로 변경
```

---

## Service / Controller 구현 가이드라인

위에서 수정한 Mapper를 기반으로 Service와 Controller를 어떻게 구성할 수 있는지 안내해 드립니다.

### 1. `BookService.java` (서비스 계층)

서비스는 컨트롤러와 매퍼 사이의 다리 역할을 하며, 비즈니스 로직을 처리합니다. `@Transactional` 어노테이션을 통해 트랜잭션 관리를 할 수도 있습니다.

```java
package example.실습.실습3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    // --- 실습 6: 뷰 생성 ---
    // 뷰 생성은 DDL로, 보통 단일 트랜잭션으로 처리됩니다.
    @Transactional
    public boolean createViews() {
        try {
            bookMapper.createDetailView();      // 1. 대출 상세 뷰 생성
            bookMapper.createMostStockView();   // 2. 재고 많은 도서 뷰 생성
            return true; // 모두 성공 시 true 반환
        } catch (Exception e) {
            // 로그를 남기는 로직 추가 가능
            System.out.println("뷰 생성 실패: " + e.getMessage());
            return false; // 하나라도 실패 시 false 반환
        }
    }

    // --- 실습 6: 뷰 조회 ---
    // 1. 대출 상세 뷰 조회
    public List<Map<String, Object>> getDetailViewData() {
        return bookMapper.getDetailView();
    }

    // 2. 재고 많은 도서 뷰 조회
    public List<BooksDto> getMostStockViewData() {
        return bookMapper.getMostStockView();
    }
}
```

### 2. `BookController.java` (컨트롤러 계층)

컨트롤러는 HTTP 요청을 받아 해석하고, 서비스에 작업을 위임한 뒤, 그 결과를 HTTP 응답으로 반환합니다.

```java
package example.실습.실습3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books/views") // 실습 6 관련 API들을 그룹화
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 실습 6: 모든 뷰(View)를 생성하는 API
     * [POST] /books/views
     * DDL 실행은 서버의 상태를 변경하므로 GET보다는 POST나 PUT이 적합합니다.
     */
    @PostMapping
    public ResponseEntity<String> createAllViews() {
        boolean success = bookService.createViews();
        if (success) {
            return new ResponseEntity<>("모든 뷰가 성공적으로 생성/갱신되었습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("뷰 생성 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 실습 6: 대출 상세 뷰를 조회하는 API
     * [GET] /books/views/detail
     */
    @GetMapping("/detail")
    public ResponseEntity<List<Map<String, Object>>> getDetailView() {
        List<Map<String, Object>> viewData = bookService.getDetailViewData();
        return new ResponseEntity<>(viewData, HttpStatus.OK);
    }

    /**
     * 실습 6: 재고 많은 도서 뷰를 조회하는 API
     * [GET] /books/views/most-stock
     */
    @GetMapping("/most-stock")
    public ResponseEntity<List<BooksDto>> getMostStockView() {
        List<BooksDto> viewData = bookService.getMostStockViewData();
        return new ResponseEntity<>(viewData, HttpStatus.OK);
    }
}
```

이 가이드라인을 참고하여 코드를 수정하고 완성해나가시면 실습 6번을 성공적으로 마무리하실 수 있을 겁니다. 궁금한 점이 있다면 언제든지 다시 질문해주세요!