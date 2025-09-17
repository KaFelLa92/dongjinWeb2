import { useState } from "react"

export default function Component7 ( props ) {

    // [1] useState 변수 선언 : 구문 분해 이용한 useState 반환값을 변수화
    // 1. import { useState } from "react"
    // 2. const [ 변수명 , set변수명 ] = useState( 초기값 );
    // * 변수명은 카멜표기법 , set변수명은 변수명 앞에 set 붙인다.
    const [ count , setCount ] = useState( 0 );
    // 3. 특정한 useState 값을 변경
    const countAdd = () => {
        // setXXX(새로운값); // 만일 값이 변경되었을때 해당 컴포넌트 재실행(재렌더링)
        // 주의할점 : 값이 (주소)변경되는 전제조건 , 1 -> 2 , 유재석 -> 강호동
        const newValue = count + 1
        setCount(newValue);  // 훅(갈고리 : 특정한 기능을 실행하면 다른 기능들도 실행)
    }

    // [2] useState로 리터럴이 아닌 객체, 배열 상태 변환하기 
    const [array , setArray] = useState( ['수박'] );
    const arrayAdd = ( ) => { // 주의할 점은 useState는 수박을 관리하는 게 아니라, 수박을 포함하는 []를 관리함
        array.push('사과');
        // 데이터를 복사하여, 데이터는 동일하지만 새로운 객체(주소) 만든다 -> 객체/배열 복사
        // setArray(array); 이거 안 됨. 주소값 변경 목적으로는 ...을 써야함
        setArray( [...array] ) // 스프레드 연산자를 쓰면 사과 쌓임
        // setArray( [...array , '사과' ] ) 이렇게 써도 됨
    }

    // [3] 리액트에서 input value 사용하기
    const [ data , setData ] = useState(''); // 초기값 공백
    const dataAdd = ( event ) => {
        // onChange가 실행되었을 때 event(이벤트 결과 정보)가 함수의 매개변수로 전달된다.
        console.log(event.target.value); // onChange가 발동한 마크업의 입력받은 값 가져오기
        // 입력받은 값을 useState로 변경한다. setXXX( )
        setData( event.target.value );
    } 

    return (<>
        <h3> useState 예제1 : {count} </h3>
        <button onClick={countAdd}> count 증가 {count} </button>
        <h3> useState 예제2 : {array} </h3>
        <button onClick={arrayAdd}> 사과 담기 </button> 
        <h3> useState 예제3 : </h3>
        <input value={data} onChange={dataAdd}/>
        <input value={data} onChange={ (e) => {setData(e.target.value)} } /> {/* 함수 추가 안하고 간단하게 쓰는 방법 */}
    </>)
} // func end

/*
    1. 데이터/자료 : 값
    2. 자료타입 : 값의 분류(한국 분리수거 VS 미국 분리수거)
        - 기본타입(리터럴(공유/static)) : 미리 만들어진 데이터) VS 참조타입(객체/주소값)
    3. 자료의 주소값 변경 기준
        1 -> 2 : 리터럴/(주소) 변경
        'a' -> 'b' : 주소 변경
        { name : 유재석 } -> { name : 유재석 , age : 40 } : 주소 변경 X (객체 값이 변한 거지, 객체 속성이 변한 게 아님)
        가 : 유니코드 a : 아스키코드
        [ '수박' ] -> [ '수박' , '사과' ] : [] 주소변경 X
        105동=[ 수박(101호) ] -> 105동=[ 수박(101호) , 사과(102호) ]
        [...105동] --> 106동[ 수박(101호) , 사과(102호) ] 

    4. 예시
    let a = 1(101번지);
    let b = 1(101번지);
    let c = "유재석"(102번지);
    let d = "유재석"(102번지);
    let f = { age : 1 } (103번지) 객체 {} 자체가 새로운 주소값을 갖는다
    let g = { age : 1 } (104번지)
    let h = [ 1 ] (105번지)
    let k = [ 1 ] (106번지)
*/