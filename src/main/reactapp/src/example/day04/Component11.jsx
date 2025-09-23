import { useEffect, useRef, useState } from "react";

export default function Component11(props) {

    // [1] 렌더링 하지 않고 데이터를 참조하는 훅 , useRef vs useState
    const inputRef = useRef(null);      // 1. import { useRef } from "react"; 자동완성
                                        // 2. useRef( 초기값 )

    const onAdd = () => {
        console.log(inputRef);          // 3. inputRef 현재 참조중인 객체 정보 { current : 참조값 }
        console.log(inputRef.current);  // 4. useRef.current : 참조값 , <input>
        inputRef.current.focus();       // - 포커스( 마우스커서 )
        console.log(inputRef.current.value) // 단순입력이라면 useState보다 useRef가 좀 더 코드 단순.
    }

    // [2] useState와 useRef 차이점
    const [count, setCount] = useState(0);
    const countRef = useRef( count );   // 1. useRef( 초기값 )
        // 2. count가 변경될 때마다 
    // useEffect( () => {} , []) : 특정 상태가 변경될 때마다 실행되는 훅(함수)
    useEffect( () => { 
        countRef.current = count;

    } , [count])    // count의 상태가 변경되면 해당 함수 (자동) 실행

    // [3] 
    const formRef = useRef();
    const onTrans = () => {
        console.log(formRef.current); // 폼 내용물들을 한번에 가져와서 자바(스프링)에게 전송
        console.log(formRef.current.elements['textData'].value)
        console.log(formRef.current.querySelector('.textData').value);
    }

    return (<>
        <h3> useRef 예제1 : 입력 </h3>
        <input ref={inputRef} onKeyDown={(e) => e.key == 'Enter' && onAdd()} />
        <button onClick={onAdd}  > 등록 </button>
        <h3> useRef 예제2 : 이전값 기억 </h3>
        <p> 현재 count : {count} / 이전 count : {countRef.current} </p> {/* 이전 값 기억은 검색 등에서 활용 */}
        <button onClick={(e) => { setCount(count + 1) }} > 증가 </button>
        <h3> useRef 예제3 : 입력 폼 </h3>
        <form ref={ formRef }> {/* form 안에 name을 써야 dto에서 체크할 수 있음 */}
            <input name="textData" id="textData" class="textData" />
            <select name="selectData">
                <option> 바나나</option>
            </select>
            <textarea name="text2Data"> </textarea>
            <button type="button" onClick={onTrans}> 폼 전송 </button> 
            {/* form 안에서는 type에 button 꼭 써야함! */}
        </form>
    </>)

} // func end



