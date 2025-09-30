package example.day11;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// [2] 인터페이스 : 주로 추상메소드(구현부없는)를 정의한다.
interface Calculator {
    int plus( int x , int y);
    // 생성자 없어서 객체를 만들 수 없다. 단 구현부{}에 생성자를 넣어주면 가능
}

public class Example1 {

    // [1] 일반 static 메소드 정의
    public static int plus (int x , int y){
        return x + y;
    }

    public static void main(String[] args) {

        // [1] 일반 메소드 호출 : static일 때 객체가 필요없다.
        int result = plus(3 , 5);
        System.out.println("[1] 일반 메소드 호출 : " + result);

        // [2] 인터페이스 추상메소드 호출 : 1) 구현체 2)익명구현체
        // 1) implements 구현체 만든다.
        // 2) 익명구현체(일회성) : new 인터페이스(){ 구현 };
        Calculator cal1 = new Calculator() {
            // 오버라이딩 : 상속 또는 추상 메소드를 재구현
            @Override
            public int plus(int x, int y) {
                return x + y;
            }
        };
        // [3] 람다식으로 익명 구현체 , java 8 , ( 매개변수 ) -> { 구현부 }
        Calculator cal2 = ( x , y ) -> x + y;
        int value2 = cal2.plus(3, 5);
        System.out.println("[3] 람다식 메소드 호출 : " + value2);

        // [4] 람다 표현식을 사용하는 *함수형* 인터페이스들
        // * 제네릭 : 인스턴스(객체) 생성시 인스턴스(객체)내 멤버들의 타입 정의
        // [4-1] Function< T , R > : T 입력받아서 R 결과를 반환
        Function<Integer, Integer> function = x -> x * 2;
        System.out.println("[4] Function : " + function.apply(3)); // 6

        // [4-2] Supplier< T > , 입력 없이 T 결과를 반환 ,
        Supplier< Double > supplier = ( ) -> Math.random();
        System.out.println("[5] supplier : " + supplier.get());

        // [4-3] Consumer< T > , T 입력 받아서 결과가 없음 , .accept( T ) 메소드 사용
        Consumer< String > consumer = (str) -> System.out.println( str );
        consumer.accept("[6] consumer : 안녕하세요");

        // [4-4] Predicate< T >
        Predicate< Integer > predicate = x -> x % 2 == 0; // 짝수 true
        System.out.println("[7] predicate : " + predicate.test(3));



    } // main end

} // class end
