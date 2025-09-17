import { useState } from "react"

export default function Component6 ( props ){
    
    // [1] 리액트에서 상태관리 라이브러리(메타에서 제공하는 함수) 사용하기
    // useState 사용법
    // 1. useState 자동완성으로 import 만들기
    // 2. useState( 초기값 );
    // 3. 반환값 => [0] : 값 , [1] : 재렌더링함수
    const variable2 = useState( '유재석');
    console.log(variable2);
    console.log(variable2[0]); // 유재석 : 0번째 인덱스에 useState가 관리하는 값이 들어있다
    console.log(variable2[1]); // f : 1번째 인덱스에 값이 변경되면 (??) 실행되는 함수가 들어있다
    const changeFunc = () => {
        variable2[0] = '강호동';
        variable2[1]('강호동');
        console.log(variable2[0])
    }

    return (<>
        <h3> useState 상태 관리 </h3>
        <h4> useState의 초기값 : {variable2[0]} </h4>
        <h4> useState의 값 변경 <button onClick={ changeFunc }> 변경 </button> </h4>
    </>)
}