import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { logout } from "../../userSlice";

export default function Header(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { isAuthenticated, userInfo } = useSelector((state) => state.user) // 구문분해
    console.log(isAuthenticated);

    // [2] logout
    const logoutHandle = () => {
        alert('로그아웃 되었습니다. 로그인 화면으로 이동합니다.')
        navigator('/login')
        dispatch(logout())
    }

    // 

    return (<>
        <div>
            <ul>
                <li> <Link to="/"> 홈 </Link></li>
                {isAuthenticated ? (<>
                    <li> <span> 안녕하세요. {userInfo.name} 님. </span></li>
                    <li> <Link to="/dashboard"> 대시보드 </Link></li>
                    <li> <Link to="/profile"> 프로필 </Link></li>
                    <li> <Link to="/login" onClick={logoutHandle}> 로그아웃 </Link></li>
                </>
                ) : (
                    <li> <Link to="/login"> 로그인 </Link> </li>
                )}
            </ul>
        </div>
    </>)
}