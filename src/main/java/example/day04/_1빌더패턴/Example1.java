package example.day04._1빌더패턴;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// (2) 설계 클래스
// @AllArgsConstructor
// @NoArgsConstructor
// @Data
@Builder // 빌더 패턴
@Data   // 게터 세터 투스트링
class MemberDto {
    // 1. 멤버변수 : (메모리) 객체 생성시 속성
    private String name;
    private int age;

    // 2. 생성자 : (초기화) 객체 생성할때 사용하는 초기 메소드
    // 2-1 모든 매개변수 갖는 전체 생성자 @AllArgsConstructor
    public MemberDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // 2-2 매개변수 없는 기본 생성자 @NoArgsConstructor
    public MemberDto() { }
    // 2-3 개별 생성자들
    public MemberDto(String name) { this.name = name; }
    public MemberDto(int age) { this.age = age; }

    // 3. 메소드 : (함수-설계 기반) 객체의 행동/이벤트/함수

} // class end

// (1) 실행 클래스
public class Example1 {
    public static void main(String[] args) {
        // 1. 객체를 생성할 때 사용되는 메소드 : new MemberDto()
        MemberDto dto1 = new MemberDto();    // 기본생성자
        // 2. 
        MemberDto dto2 = new MemberDto("유재석" , 40); // 전체생성자
        // 3-1. 존재하지 않는 생성자 불가능
        // MemberDto dto3 = new MemberDto(true);
        // 3-2. 매개변수 순서 바꾸면 안 됨.
        // MemberDto dto4 = new MemberDto(40 , "유재석");

        // 즉 : 생성자는 정해진 매개변수에 따라 사용된다. 유연성이 떨어짐

        // 4. *** 롬복 패턴 ***
        // 즉 : 생성자 유무와 상관없이 메소드로 객체 초기화
        MemberDto dto5 = MemberDto.builder().build();
        System.out.println("dto5 = " + dto5); // soutv + 엔터
        // dto5 = MemberDto(name=null, age=0)

        MemberDto dto6 = MemberDto.builder().name("유재석").build();
        System.out.println("dto6 = " + dto6);
        // dto6 = MemberDto(name=유재석, age=0)
        
        MemberDto dto7 = MemberDto.builder().age(40).name("유재석").build();
        System.out.println("dto7 = " + dto7);
        // dto7 = MemberDto(name=유재석, age=40)

    } // main end
} // class end
