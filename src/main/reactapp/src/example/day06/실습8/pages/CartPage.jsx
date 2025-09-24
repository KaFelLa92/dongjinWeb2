import { useDispatch, useSelector } from "react-redux";
import { remove } from "../store/cartSlice";

export default function CartPage(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const cartItems = useSelector((state) => state.cart.cartInfo);

    // [2] 총 합계 계산 함수
    const totalPrice = cartItems.reduce((sum, item) => {
        return sum + (item.price * item.stock)
    } , 0) // 0은 초기값


    // [3] 장바구니 비우기 함수
    const removeCart = async () => {
        dispatch(remove())
    }

    return (<>
        <h3> 장바구니 페이지 </h3>
        <div>
            {cartItems.length == 0 ? (
                <p> 장바구니가 비어있습니다. </p>
            ) : (
                <>
                    {cartItems.map((item) => (
                        <div>
                            {item.name} | 수량 : {item.stock}개 | 가격 : {(item.stock * item.price).toLocaleString()}원
                        </div>
                    ))}
                    < h4 > 총 합계 : {totalPrice.toLocaleString()}원 </h4>
                    <button type="button" onClick={removeCart}> 장바구니 비우기 </button>
                </>
            )}
        </div >
    </>
    )
}