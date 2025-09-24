import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { add } from "../store/cartSlice";

export default function MenuPage(props) {

    // [*] useRef로 form 참조해서 메뉴 담을 준비
    const formRef = useRef(null);

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

    // [2] 장바구니 담기 함수
    const addCart = async (clickItem) => {
        dispatch(add(clickItem))
        console.log(clickItem.name + "이(가) 장바구니에 추가되었습니다") 
    }

    // [3] 리턴(키오스크 화면)
    return (<>
        <h3> 메뉴 페이지 </h3>
        {menu.map((m) => {
            return (<>
                <div key={m.id}>
                    <button type="button" onClick={() => { addCart(m) }} > {m.name} ({m.price}원) 담기 </button>
                </div>
            </>)
        })}
    </>)
}