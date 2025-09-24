import { useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export default function MenuPage(props) {

    // [*] useRef로 form 참조해서 메뉴 담을 준비
    const formRef = useRef(null);

    // [*] 셀렉트박스를 사용할 useState 세팅
    const [selected, setSelected] = useState("");

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { cartInfo } = useSelector((state) => state.cart)
    console.log(cartInfo);

    // [*] 샘플 메뉴
    const menu = [
        { id: 1, name: "아메리카노", price: 3000 },
        { id: 2, name: "카페라떼", price: 4000 },
        { id: 3, name: "카푸치노", price: 4500 },
    ];

    // [2] 셀렉트박스 핸들러 함수
    const selectHandle = async (e) => {
        setSelected(e.target.value)
    }

    // [3] 장바구니 담기 함수
    const addCart = async () => {
        if(!selected) {
            alert('메뉴를 선택하세요')
            return
        }
        const id = formRef.current.elements['id'].value;
        dispatch(add(id))

    }



    return (<>
        <h3> 메뉴 페이지 </h3>
        <form ref={formRef}>
            <select value={selected} onChange={selectHandle}>
                <option value=""> --- 메뉴 선택 --- </option>
                {menu.map((m) => {
                    return (<>
                        <option value={m.id}> {m.name} ({m.price}원) </option>
                    </>)
                })}
            </select>
            <button type="button" onClick={addCart}> 담기 </button>
        </form>
    </>)
}