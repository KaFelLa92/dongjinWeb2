import { useState } from "react";

export default function Task3() {

    // [1] 재고 가감
    // useState
    const [stock, setStock] = useState(0);

    // 재고 증가 함수
    const onAdd = () => {
        const addStock = stock + 1
        setStock(addStock)
        console.log("현재 재고 : " + addStock)
    }

    // 재고 감소 함수
    const onRemove = () => {
        const removeStock = stock - 1
        setStock(removeStock)
        console.log("현재 재고 : " + removeStock)
    }

    // [2] 제품명 관리
    // useState
    const [name, setName] = useState('');

    // 제품명 변경 함수
    const nameChange = ( e ) => {
        console.log(e.target.value) // target은 이벤트 발생시키는 객체 속성
        setName(e.target.value)
    }

    return (<>

        <p> 제품명: <input value={name} onChange={nameChange} /> </p>
        <p> 현재 수량: {stock} </p>
        <button onClick={onRemove}> 감소 </button>
        <button onClick={onAdd}> 증가 </button>

    </>)

}