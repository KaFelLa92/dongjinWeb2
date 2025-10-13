# Java 함수형 인터페이스와 스트림 API 요약

이 문서는 오늘 배운 함수형 인터페이스와 스트림 API 메소드를 요약합니다.

## 1. 함수형 인터페이스

함수형 인터페이스는 람다 표현식과 함께 사용되도록 설계된, 단일 추상 메소드를 가진 인터페이스입니다.

### 가. `Function<T, R>`
- **목적:** `T` 타입의 인자를 받아 `R` 타입의 결과를 반환합니다.
- **메소드:** `apply(T t)`
- **예제:**
  ```java
  Function<Integer, Integer> function = x -> x * 2;
  System.out.println(function.apply(3)); // 출력: 6
  ```

### 나. `Supplier<T>`
- **목적:** 결과물을 공급하는 역할을 합니다. 인자 없이 `T` 타입의 결과를 반환합니다.
- **메소드:** `get()`
- **예제:**
  ```java
  Supplier<Double> supplier = () -> Math.random();
  System.out.println(supplier.get());
  ```

### 다. `Consumer<T>`
- **목적:** 단일 입력 인자를 받아들이고 결과를 반환하지 않는 연산을 나타냅니다.
- **메소드:** `accept(T t)`
- **예제:**
  ```java
  Consumer<String> consumer = (str) -> System.out.println(str);
  consumer.accept("안녕하세요");
  ```

### 라. `Predicate<T>`
- **목적:** 하나의 인자에 대한 boolean 값을 반환하는 함수(술어)를 나타냅니다.
- **메소드:** `test(T t)`
- **예제:**
  ```java
  Predicate<Integer> predicate = x -> x % 2 == 0;
  System.out.println(predicate.test(4)); // 출력: true
  ```

---

## 2. 스트림 API

스트림 API는 객체 컬렉션을 함수형 스타일로 처리하는 데 사용됩니다.

### 가. `forEach()`
- **목적:** 스트림의 각 요소에 대해 작업을 수행합니다.
- **예제:**
  ```java
  numbers.stream().forEach(x -> System.out.println(x));
  ```

### 나. `map()`
- **목적:** 스트림의 각 요소를 다른 객체로 변환합니다.
- **예제:**
  ```java
  List<Integer> doubled = numbers.stream().map(x -> x * 2).collect(Collectors.toList());
  ```

### 다. `filter()`
- **목적:** 주어진 조건(predicate)에 따라 스트림의 요소를 필터링합니다.
- **예제:**
  ```java
  numbers.stream().filter(x -> x % 2 == 0).forEach(System.out::println);
  ```

### 라. `sorted()`
- **목적:** 스트림의 요소를 정렬합니다.
- **예제:**
  ```java
  numbers.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
  ```

### 마. `distinct()`
- **목적:** 스트림에서 중복된 요소들을 제거한 스트림을 반환합니다.
- **예제:**
  ```java
  List<Integer> distinctNumbers = numbers.stream().distinct().collect(Collectors.toList());
  ```

### 바. `limit()`
- **목적:** 스트림의 길이를 `maxSize` 이하로 제한합니다.
- **예제:**
  ```java
  numbers.stream().limit(5).forEach(System.out::println);
  ```

### 사. `reduce()`
- **목적:** 스트림의 요소에 대해 연관 누적 함수를 수행하고 선택적 결과를 반환합니다.
- **예제:**
  ```java
  int sum = numbers.stream().reduce(0, (acc, val) -> acc + val);
  int product = numbers.stream().reduce(1, (acc, val) -> acc * val);
  ```