export default function Component5 ( props ) {

    const items = [ '사과' , '배' , '딸기' , '참외' , '수박' , '복숭아' , '코코넛' ]

    const newItems = items.forEach((item) => {console.log(item); return item; })
    console.log(newItems)

    // map은 리턴 가능
    const newItems2 = items.map( (item) => {console.log(item); return item; })
    console.log(newItems2)

    // 함수 : 변수 변화를 주는 함수
    const onAdd = ( ) => {
        items.push('수박');
        console.log(items);
    }
    // 함수(컴포넌트)는 1번 호출에 1번 리턴 , 즉 : 한 번 return된 jsx는 수정 불가능
    // 해결책 : return 다시하기 --> 함수 다시 호출 --> 재렌더링 : 훅(useState 함수)
    return(<>
        <h3> JSX 반복문 forEach : return 없다 vs map : return 있다 </h3>
        <h2> 따라서 리액트에서는 반환값 있는 map만 씁니다 </h2>
        <ul>
            { /* items.forEach( (item) => {<li> {item} </li>}) forEach는 리턴 안 됨*/ }
            { items.map( (item) => { return <li> {item} </li>}) }
        </ul>
        <button onClick={onAdd}> item 추가 </button>

    </>)
} // func end