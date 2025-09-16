# MyBatis 어노테이션 기반 문법 요약

이 문서는 제공된 `BatisMapper.java` 예제를 기반으로, 어노테이션을 사용하는 기본 MyBatis 문법을 요약합니다.

## 1. `@Mapper` 어노테이션

이 어노테이션은 인터페이스에 선언하여 Spring에게 해당 인터페이스가 MyBatis 매퍼임을 알려주는 역할을 합니다. Spring은 이 인터페이스의 구현체를 자동으로 생성하여 Spring 컨테이너에 등록하며, 다른 컴포넌트에서 의존성 주입(DI)을 통해 사용할 수 있게 해줍니다.

```java
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatisMapper {
    // ... 메소드들
}
```

## 2. SQL 조작 어노테이션

MyBatis는 SQL 쿼리를 Java 코드에 직접 작성할 수 있도록 여러 어노테이션을 제공합니다.

### `@Insert`

SQL `INSERT` 구문을 정의할 때 사용합니다. 메소드의 반환 타입은 보통 `int`로 하며, 이는 삽입된 레코드(행)의 수를 의미합니다.

- **예시:**
  ```java
  @Insert("insert into student (name, kor, math) values (#{name} , #{kor} , #{math}) ")
  int save( StudentDto studentDto);
  ```
- **파라미터:** `#{...}` 구문은 `studentDto` 객체의 필드 값에 접근하기 위해 사용됩니다. (예: `#{name}`은 `studentDto.getName()`에 해당합니다.)

### `@Select`

SQL `SELECT` 구문을 정의할 때 사용합니다. 반환 타입은 쿼리 결과에 맞춰 지정해야 하며, 단일 객체, `List`, `Map` 등 다양한 형태가 될 수 있습니다.

- **예시 (전체 조회):**
  ```java
  @Select("select * from student")
  List<StudentDto> findAll();
  ```
  이 쿼리는 `student` 테이블의 모든 레코드를 반환하며, MyBatis가 각 레코드를 `StudentDto` 객체에 자동으로 매핑해줍니다.

- **예시 (개별 조회):**
  ```java
  @Select("select * from student where sno = #{sno}")
  Map<String, Object> find(int sno);
  ```
  `#{sno}`는 메소드의 파라미터 `sno` 값을 `WHERE` 절의 조건으로 사용합니다. 조회 결과는 `Map` 형태로 반환되며, 여기서 키는 컬럼명, 값은 해당 컬럼의 데이터가 됩니다.

### `@Delete`

SQL `DELETE` 구문을 정의할 때 사용합니다. 보통 삭제된 레코드의 수를 의미하는 `int`를 반환합니다.

- **예시:**
  ```java
  @Delete("delete from student where sno = #{sno}")
  int delete(int sno);
  ```
- **파라미터:** 기본 자료형 파라미터 `sno`는 `#{sno}` 형태로 직접 참조됩니다.

### `@Update`

SQL `UPDATE` 구문을 정의할 때 사용합니다. 이 역시 보통 수정된 레코드의 수를 의미하는 `int`를 반환합니다.

- **예시:**
  ```java
  @Update("update student set name = #{name} , kor = #{kor} , math = #{math} where sno = #{sno}")
  int update(StudentDto studentDto);
  ```
- **파라미터:** `@Insert` 예시와 마찬가지로, `studentDto` 객체의 필드 값들이 수정할 데이터로 사용됩니다.

## 3. `#{}`를 이용한 파라미터 매핑

`#{}` 구문은 MyBatis에서 SQL 쿼리로 파라미터를 전달하는 표준 방식입니다. 이 방식은 내부적으로 `PreparedStatement`를 사용하여 SQL 인젝션 공격을 방지하므로 안전합니다.

- **DTO/객체 사용 시:** `#{필드명}`은 해당 객체의 getter 메소드를 호출하여 값을 가져옵니다. (예: `#{name}`은 `studentDto.getName()`을 호출)
- **기본 자료형(int, String 등) 사용 시:** `#{파라미터명}`은 메소드의 파라미터를 직접 참조합니다. (예: `delete` 메소드의 `#{sno}`)