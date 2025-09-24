import { Link } from "react-router-dom";

export default function Header (props) {

    return(<>
        <h3> 헤더 페이지 </h3>
        <div>
            <ul> <Link to="/"> 홈 </Link> </ul>
            <ul> <Link to="/menu"> 메뉴 선택 </Link> </ul>
            <ul> <Link to="/cart"> 장바구니 </Link> </ul>
        </div>
    </>)
}