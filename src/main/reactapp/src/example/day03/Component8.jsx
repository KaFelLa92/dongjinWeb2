import { useEffect, useState } from "react"

export default function Component8 ( props ) {

    // 리액트 훅(갈고리) : 1. useState 2. useEffect
    // [1] useEffect : 특정한 시점(현재 컴포넌트의 실행시점과 종료시점)에 함수가 실행되는 구조
    // [2] 사용법 :
    //      import { useEffect } from "react"
    //      3-1 : useEffect( 정의함수명 ) 또는 useEffect( () => {} )
    //      3-2 : useEffect( () => {} , [ 상태변수1 , 상태변수2 ] )    
    //          * [ ] 의존성배열 : 상태변수를 대입하여 상태변수가 재렌더링하면 useEffect 실행
    //      3-3 : useEffect( () => {} , [ ] )
    // [3] 시점 :   1. 컴포넌트 탄생(최초실행) 
    //              2. 컴포넌트 인생/업데이트(재실행/재렌더링)
    //              3. 컴포넌트 죽음 ummount (컴포넌트가 화면에서 없어질때)
    // 3-1 : 1. 최초실행 2. 재실행
    useEffect( () => { console.log('(3-1)컴포넌트(함수) 탄생=mount(최초실행)')} )

    const [ count, setCount] = useState(0);
    // 3-2 : 1. 최초실행 2. [특정상태변경 시] 재실행
    useEffect( () => { 
        console.log('(3-2)컴포넌트(함수) 탄생, *특정* 업데이트=update(재실행)')
    } , [ count ]); // 의존성배열에 대입된 count가 setCount가 될때 현재 useEffect (자동) 실행

    // 3-3 : 1. 최초실행 * 의존성배열이 비어있음
    useEffect( () => { console.log('(3)한 번만 실행' )} , [ ])

    // * 
    const [ count2 , setCount2 ] = useState(0);


    return(<>
        <h3> useEffect 예제1 : {count} </h3>
        <button onClick={ (e) => { setCount(count + 1) }}> 버튼1( 3-1 / 3-2 ) </button>
        <button onClick={ (e) => { setCount2(count2 + 1)}}> 버튼2( 3-1 ) </button>
    </>)
} // func end