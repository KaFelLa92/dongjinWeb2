import { BrowserRouter, Link, Route, Routes, useNavigate } from "react-router-dom";
import './Task6.css'
import { useRef } from "react";

// [1] 홈 화면

function Home(props) {
    return (<>
        <div id="mainContainer">
            <h2> 홈 페이지 </h2>
            <p> 좌측 메뉴에서 회원가입 또는 로그인으로 이동해보세요.</p>
        </div>
    </>)
}

// [2] 회원가입 화면
function Signup(props) {

    // [*] 입력 상자를 참조하는 useRef
    const idRef = useRef(null);
    const pwdRef = useRef(null);

    // [*] 라우터 전용 페이지 전환 함수
    const navigate = useNavigate();

    // 1) 회원가입 함수
    const onSignup = async () => {
        console.log(idRef.current.value);
        const id = idRef.current.value;
        const pwd = pwdRef.current.value;
        const obj = { id, pwd };
        // * axios를 이용한 서버 통신 했다고 가정
        if (obj.id == "admin" && obj.pwd == "1234") {
            alert('관리자 회원가입 되었습니다')
            navigate('/login'); // 라우터방식
        } else {
            alert('회원가입 되었습니다')
            navigate('/login'); // 라우터방식
        }
    }

    // 2) 리턴
    return (<>
        <div id="mainContainer">
            <div class="inputContainer">
                <h2> 회원가입 페이지 </h2>
                <input placeholder="아이디" name="id" ref={idRef} />
                <input placeholder="비밀번호" name="pwd" ref={pwdRef} />
                <button type="button" onClick={onSignup}>회원가입</button>
            </div>
        </div>
    </>)
}

// [3] 로그인 화면
function Login(props) {

    // [*] 입력 정보가 담긴 form을 참조하는 useRef
    const formRef = useRef(null);

    // [*] 라우터 전용 페이지 전환 함수
    const navigate = useNavigate();

    // 1) 로그인 실행 함수
    const onLogin = async () => {
        console.log(formRef.current);
        const id = formRef.current.elements['id'].value;
        const pwd = formRef.current.elements['pwd'].value;
        // * axios를 이용한 서버 통신 했다고 가정
        if (id == "admin" && pwd == "1234") {
            alert('관리자 로그인 되었습니다')
            navigate('/') // 라우터방식으로 홈 페이지로 전환
        } else {
            alert('로그인 되었습니다')
            navigate('/') // 라우터방식으로 홈 페이지로 전환
        }

    }

    // 2) 리턴
    return (<>
        <div id="mainContainer">
            <form class="inputContainer" ref={formRef}>
                <h2> 로그인 페이지 </h2>
                <input placeholder="아이디" name="id" />
                <input placeholder="비밀번호" type="password" name="pwd" />
                <button type="button" onClick={onLogin}>로그인</button>
            </form>
        </div>
    </>)
}

// [*] 404 페이지

function Page404(props) {
    const navigate = useNavigate();
    const Locate = () => {
        navigate("/");
    }

    return (<>
        <div id="mainContainer">
            <h2> 존재하지 않는 페이지입니다. </h2>
            <button onClick={Locate}> 홈페이지로 </button>
        </div>
    </>)
}


// [*] 라우터 컴포넌트
export default function Task6(props) {

    return (<>
        <BrowserRouter>
            <div id="sidebarContainer">
                <ul id="sidebarUl">
                    <li> <Link to="/"> 홈 </Link> </li>
                    <li> <Link to="signup">회원가입</Link> </li>
                    <li> <Link to="login"> 로그인 </Link> </li>
                </ul>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="*" element={<Page404 />} />
                </Routes>
            </div>
        </BrowserRouter>
    </>)

}