import { useDispatch, useSelector } from "react-redux";
import { add } from "../store/cartSlice";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function MenuPage(props) {

    // [*] 샘플 메뉴
    const menu = [
        { id: 1, name: "아메리카노", price: 3000 },
        { id: 2, name: "카페라떼", price: 4000 },
        { id: 3, name: "카푸치노", price: 4500 },
    ];

    // [*] 안내메시지용 useState
    const [message, setMessage] = useState("");

    const nav = useNavigate();

    // [1] store 가져오기
    const dispatch = useDispatch();
    const cartItems = useSelector((state) => state.cart.cartInfo);

    // [2] 장바구니 담기 함수
    const addCart = async (m) => {
        dispatch(add(m))
        setMessage(m.name + " 1개 담았습니다")
        console.log(m.name + "이(가) 장바구니에 추가되었습니다")
    }

    // [*] 장바구니 이동 함수
    const toCart = async () => {
        nav('/cart')
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
        <div> {message} </div>
        {cartItems.length == 0 ? (
            <p> 결제하실 음료를 클릭해주세요. </p>
        ) : (
            <div>
                <p> 결제하시겠습니까? </p>
                <button onClick={toCart}> 장바구니 페이지로 </button>
            </div>
        )}
    </>)
}