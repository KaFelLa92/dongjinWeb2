import { useDispatch, useSelector } from "react-redux"
import { login, logout } from "../../userSlice";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginPage(props) {

    // [*] useRef로 form 참조하기 (전역에 쓰기)
    const formRef = useRef(null);

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { isAuthenticated } = useSelector((state) => state.user)
    console.log(isAuthenticated);

    //[*] 로그인 성공시 홈화면으로 이동
    const navigate = useNavigate();

    // [2] 로그인처리
    const loginHandle = async () => {
        const id = formRef.current.elements['id'].value;
        const pwd = formRef.current.elements['pwd'].value;
        if (id == "admin" && pwd == '1234') {
            // dispatch(login())
            const obj = { id: 3, name: "유재석" } // login action에 보낼 데이터
            alert(obj.name + '님 관리자 로그인 되었습니다. 홈 화면으로 이동합니다.')
            dispatch(login(obj)) // [1-3] 
            navigate("/")
        } else if (id == "user" && pwd == "0000") {
            const obj = { id: 5, name: "강호동" }
            dispatch(login(obj))
            alert(obj.name + '님 로그인 되었습니다. 홈 화면으로 이동합니다.')
            navigate("/")
        }
        else {
            alert('아이디 / 비밀번호를 확인해주세요.')
            return;
        }
    }

    // [3] 로그아웃처리
    const logoutHandle = async () => {
        alert('로그아웃 되었습니다.')
        dispatch(logout())
    }

    // [4] 리턴
    return (<>
        <h3> 로그인 페이지 </h3>
        {isAuthenticated == false
            ?
            <form ref={formRef}>
                <input placeholder="아이디" name="id" />
                <input placeholder="비밀번호" name="pwd" type="password" />
                <button type="button" onClick={loginHandle} > 로그인 버튼 </button>
            </form>
            :
            <div>
                {formRef.current.elements['id'].value == "admin" ? <p> 반갑습니다 관리자님 </p>
                    : <p> 반갑습니다 사용자1님 </p>
                }
                <button type="button" onClick={logoutHandle} > 로그아웃 버튼 </button>
            </div>
        }

    </>)
}