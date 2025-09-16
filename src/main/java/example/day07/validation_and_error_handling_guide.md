# Spring Boot 백엔드 유효성 검사 및 에러 핸들링 가이드

이 문서는 Spring Boot 백엔드에서 데이터 유효성 검사를 수행하고, 그 결과를 프론트엔드로 전송하여 처리하는 전체 과정을 설명합니다.

## 1. 입력값 유효성 검사 (Validation)

사용자로부터 받은 데이터(Request Body)가 비어있거나, 형식에 맞지 않는 경우를 백엔드에서 검증하는 과정입니다.

### 1.1. 백엔드 설정

#### 1.1.1. 의존성 추가

`build.gradle` 파일에 `validation` 스타터를 추가합니다. (일반적으로 `spring-boot-starter-web`에 포함되어 있어 별도 추가가 필요 없는 경우가 많습니다.)

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

#### 1.1.2. DTO (Data Transfer Object)에 유효성 검사 어노테이션 추가

프론트엔드로부터 데이터를 받을 `BoardDto.java` 파일의 각 필드에 유효성 검사 규칙을 정의합니다.

- `@NotBlank`: `null`, `""`, `" "` (공백)을 모두 허용하지 않습니다.
- `@Size(max=10)`: 문자열의 최대 길이를 10자로 제한합니다.

```java
// BoardDto.java
package example.day07.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardDto {
    private int bno;

    @NotBlank(message = "본문 내용을 입력하세요.")
    private String bcontent;

    @NotBlank(message = "작성자를 입력하세요.")
    @Size(max = 10, message = "작성자 이름은 10자 이하로 작성해주세요.")
    private String bwriter;
}
```

#### 1.1.3. Controller에서 유효성 검사 실행 및 결과 처리

Controller 메소드에서 `@RequestBody`로 받는 DTO 객체 앞에 `@Valid` 어노테이션을 붙여 유효성 검사를 활성화합니다. 검사 결과는 `BindingResult` 객체에 담깁니다.

- `bindingResult.hasErrors()`: 유효성 검사 실패 시 `true`를 반환합니다.
- 실패 시, `getFieldErrors()`를 통해 모든 오류를 가져와 메시지 목록을 생성합니다.
- `ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)`: HTTP 상태 코드 400 (Bad Request)와 함께 오류 메시지 목록을 프론트엔드로 전송합니다.

```java
// BoardController.java
package example.day07.controller;

import example.day07.model.dto.BoardDto;
import example.day07.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<?> boardWrite(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        int result = boardService.boardWrite(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    // ... (boardUpdate 메소드도 동일하게 수정)
}
```

### 1.2. 프론트엔드 처리 (JavaScript 예시)

프론트엔드에서는 `fetch` API를 사용하여 백엔드에 요청을 보낸 후, 응답 상태 코드를 확인하여 분기 처리합니다.

- `response.ok` 또는 `response.status === 201`: 요청 성공.
- `response.status === 400`: 유효성 검사 실패. `response.json()`으로 오류 메시지 배열을 받아 화면에 표시합니다.

```javascript
async function submitBoard() {
    const bcontent = document.getElementById('bcontent').value;
    const bwriter = document.getElementById('bwriter').value;

    const response = await fetch('/board', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ bcontent, bwriter }),
    });

    if (response.ok) {
        alert('게시물 등록 성공!');
        location.reload();
    } else if (response.status === 400) {
        const errors = await response.json(); // ["오류 메시지 1", "오류 메시지 2"]
        alert(errors.join('\n')); // 오류 메시지를 alert으로 표시
    } else {
        alert('알 수 없는 오류가 발생했습니다.');
    }
}
```

---

## 2. 존재하지 않는 데이터 처리 (Invalid ID)

사용자가 존재하지 않는 게시물 번호(`bno`)로 조회, 수정, 삭제를 시도하는 경우를 처리합니다.
이러한 종류의 검증은 데이터베이스의 상태를 확인해야 하므로, DTO 레벨의 어노테이션만으로는 처리할 수 없으며 **서비스 또는 컨트롤러 레벨의 로직**이 필요합니다.

### 2.1. 백엔드 설정

Controller에서 Service의 반환값을 확인하여 분기 처리합니다.

- **조회 (`boardFind`)**: Service가 `null`을 반환하면, 데이터가 없다는 의미이므로 `404 Not Found` 상태 코드와 에러 메시지를 반환합니다.
- **수정/삭제 (`boardUpdate`, `boardDelete`)**: MyBatis의 `update`/`delete`는 적용된 행(row)의 개수를 반환합니다. 이 값이 `0`이면, 해당 `bno`의 데이터가 존재하지 않아 작업이 실패했다는 의미입니다. 이 경우에도 `404 Not Found`를 반환합니다.

```java
// BoardController.java (추가된 부분)

    // [3] 개별조회
    @GetMapping("/find")
    public ResponseEntity<?> boardFind(@RequestParam int bno) {
        BoardDto boardDto = boardService.boardFind(bno);
        if (boardDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(boardDto);
    }

    // [4] 개별수정
    @PutMapping("")
    public ResponseEntity<?> boardUpdate(@RequestBody @Valid BoardDto boardDto, BindingResult bindingResult) {
        // ... (입력값 유효성 검사 로직) ...

        int result = boardService.boardUpdate(boardDto);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물을 찾을 수 없거나 수정에 실패했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    // [5] 개별삭제
    @DeleteMapping("")
    public ResponseEntity<?> boardDelete(@RequestParam int bno){
        int result = boardService.boardDelete(bno);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물을 찾을 수 없거나 삭제에 실패했습니다.");
        }
        return ResponseEntity.ok(result);
    }
```

### 2.2. 프론트엔드 처리 (JavaScript 예시)

프론트엔드에서는 `404` 상태 코드를 확인하여 사용자에게 데이터가 없음을 알려줍니다.

```javascript
async function deleteBoard(bno) {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    const response = await fetch(`/board?bno=${bno}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        alert('삭제되었습니다.');
        location.reload();
    } else if (response.status === 404) {
        const errorMessage = await response.text();
        alert(errorMessage); // "해당 게시물을 찾을 수 없거나 삭제에 실패했습니다."
    } else {
        alert('알 수 없는 오류가 발생했습니다.');
    }
}
```
