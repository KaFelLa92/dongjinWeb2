package example.day11;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Member {
    String name;
    Member( String name ) {
        this.name = name;
    }
}

public class Example3 {
    public static void main(String[] args) {

        // [*] 메소드 레퍼런스API : *이미 정의된* 메소드를 참조해서 사용하는 표현식 + 람다
        // 클래스명.정적메소드명() vs 클래스명 :: 메소드명
        // 객체명.메소드명() vs 객체명 :: 메소드명

        // [1] 예1 : static 정적메소드
        System.out.println(Integer.parseInt("123")); // 문자타입 --> 숫자타입 변환
        Function<String , Integer> func = Integer::parseInt;
        System.out.println(func.apply("123"));

        // [2] 예2 : 일반메소드
        List<String> names = List.of("유재석" , "강호동" , "신동엽");
        for( int i = 0; i < names.size(); i++ ){
            System.out.println(names.get(i));
        }
        names.stream().forEach( name -> System.out.println(name));
        names.stream().forEach( System.out :: println );
        // 위 반복문 3개가 다 같은 결과를 출력

        // [3] 예3 , 생성자 , 클래스명 :: new는 생성자를 의미
        names.stream().forEach(name -> new Member(name));
        names.stream().forEach( Member :: new);

        List<Member> newMember = names.stream()
                .map(Member::new)
                .collect(Collectors.toList());
        System.out.println("newMember = " + newMember);


    } // main end
} // class end

// 정적메소드 : 객체(인스턴스)없이 사용 가능한 메소드 , 즉] static 있는
// 멤버메소드 : 객체 통해 사용 가능한 메소드 , 즉] static 없는



