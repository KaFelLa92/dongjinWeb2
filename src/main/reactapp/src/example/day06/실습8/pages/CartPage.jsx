import { useDispatch } from "react-redux";

export default function CartPage(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { cartInfo } = useSelector((state) => state.cart)
    console.log(cartInfo);

    return (<>
        <h3> 장바구니 페이지 </h3>
        <div> 
            <h4> 총 합계 </h4>
        </div>
    </>)
}