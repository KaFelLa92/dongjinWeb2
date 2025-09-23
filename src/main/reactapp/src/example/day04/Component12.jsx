
import { BrowserRouter, Link, Route, Routes, useNavigate, useParams, useSearchParams } from 'react-router-dom'

// [1] 메인페이지 컴포넌트
function Home(props) {
    return (<>
        메인페이지
    </>)
}
// [2] 소개페이지 컴포넌트
function About(props) {
    return (<>
        소개페이지
    </>)
}
// [3] 마이페이지 컴포넌트 : 쿼리스트링 매개변수 전달
function MyPage(props) {
    // JS(web1) 방식 : const name = new URL(location.href).searchParams.get('name')
    // 리액트 queryString 방식
    const [searchParams] = useSearchParams();   // 1. const [ searchParams ] = useSeachParams(); 구조 분해
    const name = searchParams.get('name');      // 2. const 변수명 = searchParams.get(매개변수명);
    const age = searchParams.get('age');

    return (<>
        <h3> 마이페이지 </h3>
        <p> 이름 : {name} / 나이 : {age} </p>
    </>)
}

// [4] 제품 소개 페이지 : path 매개변수 전달
function Product(props) {
    // ** 리액트 path 방식 ** : /product/코카콜라/1000 vs /product?name=코카콜라&price=1000
    const { name, price } = useParams(); // 1. const { 매개변수명1 , 매개변수명2 } = useParams();

    return (<>
        <h3> 제품 소개 페이지 </h3>
        <p> 제품명 : {name} / 가격 : {price} </p>
    </>)
}

// [5] 404 페이지 컴포넌트 (배포 단계에서 제작)
function Page404(props) {

    // 5-1 : useNavigate() 반환값 저장
    const navigate = useNavigate();
    const Locate = () => {
        // HTML 페이지 전환 : <a> , location.href  = "경로"
        // location.href="/" 얘는 깜빡거림

        // 라우터 페이지 전환 : <Link> , navigate( "경로" )
        // 5-2 : useNavigate() 반환값인 navigate 사용. 얘는 안 깜빡
        navigate("/"); 
    }

    return (<>
        <h3> 404 페이지 : 존재하지 않는 페이지입니다. </h3>
        <a href="/" > 홈으로1(get방식) </a>
        <Link to="/"> 홈으로2(라우터방식) </Link> {/* 이거 쓰셈 위에거는 블링크 발생 */}
        <button onClick={Locate}> 홈으로3 </button>
    </>)
}



// [*] 라우터 : 하나의 컴포넌트가 여러 컴포넌트를 연결 * 가상의 URL 만들기
// 1. 라우터 라이브러리 설치 : npm i react-router-dom , * 리액트 종료된 상태에서 설치
// 2. 
export default function Component12(props) {

    return (<>
        <BrowserRouter>
            <ul>
                <a href='/'> 메인페이지(Home/html 방식) </a> {/* 얘는 깜빡거려서 탈락 */}
                <Link to="/"> 메인페이지(home/react라우터방식) </Link> {/* 진짜 URL이 아닌 가상의 URL로 이동할 때는 <Link to="가상URL"> 마크업 사용 */}
                <Link to="/about"> 소개페이지 </Link>
                <Link to="/mypage"> 마이페이지1(쿼리스트링x) </Link>
                <Link to="/mypage?name=유재석&age=40"> 마이페이지2(쿼리스트링o) </Link>
                <Link to="/product" > 제품 소개 페이지1 </Link> {/* 링크에 패스 없으면 켜지지도 않음 */}
                <Link to="/product/코카콜라/1000" > 제품 소개 페이지2 </Link>
            </ul>
            <Routes> {/* 가상의 URL 정의하고, 정의한 URL과 매핑할 컴포넌트 정의 */}
                <Route path='/' element={<Home />} />           {/* Route path="가상URL정의" element={<컴포넌트/>} */}
                <Route path='/about' element={<About />} />
                <Route path='/mypage' element={<MyPage />} />
                <Route  
                    path='/product/:name/:price' 
                    element={<Product />} />    {/* PathVariable 형식 */}
                {/* 만약 존재하지 않는 가상 URL 요청 시 , 404 , 500 등 컴포넌트 페이지를 만들어줘야함! */}
                <Route path='*' element = {<Page404 />} />
            </Routes>
        </BrowserRouter>
    </>)
}