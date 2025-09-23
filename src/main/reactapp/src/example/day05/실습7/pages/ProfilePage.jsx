import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";

export default function ProfilePage(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { isAuthenticated , userInfo } = useSelector((state) => state.user)
    console.log(isAuthenticated);

    // [2] 비정상 경로 날려버릴 useNavigate 가져오기
    const navigate = useNavigate();

    if (isAuthenticated == false) {
        alert('정상적인 경로가 아닙니다. 로그인해주세요.')
        navigate("/")
        return null;
    }

    return (<>
        <h3> 프로필 페이지 </h3>
        <div>
            {userInfo.name} 님의 마이페이지입니다.
        </div>
    </>)
}