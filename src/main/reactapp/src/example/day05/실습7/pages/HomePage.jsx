import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";

export default function HomePage(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { isAuthenticated } = useSelector((state) => state.user)
    console.log(isAuthenticated)

    return (<>
        {isAuthenticated == false ? (<>
            <h3> 그때만나양 메인페이지 </h3>
            <p> 약속을 잘 지키는 양치기 소년소녀가 되어, 당신의 목장을 만들어보세요 :) </p>
            <p> <Link to="/login"> 지금 시작하기 </Link></p>
        </>) : (<>
            <div>
                <h3> 목장에 오신 걸 환영합니다!</h3>
                <p> 다른 사람과 약속을 만들어보세요. 약속의 평가에 따라 양이 추가될 수 있습니다. </p>
                <p> 양 : 메에에에 </p>
            </div></>)
        }
    </>)
}