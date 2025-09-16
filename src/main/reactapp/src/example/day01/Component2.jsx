// 함수 안에 다른 함수 호출 가능
// 즉] 컴포넌트 안에 다른 컴포넌트 호출 가능

// (1) function
// (2) 컴포넌트명 : 아무거나 하되 default 컴포넌트 파일명 일치
// (3) ( props ) : 속성(property)으로 <컴포넌트 속성=값 속성=값 />
// (4) { } : 중괄호 안에서 JS와 HTML 작성한다.
// (5) return : HTML과 JS가 혼합된 JSX 문법을 사용할 수 있다. JSP(HTML+JAVA) JSX(HTML+JS)
// (6) jsx파일내 export default 함수를 정의한다.

// [1] 메인페이지
const Component2 = ( props ) => {
    const name = "유재석"; // JS 코드
    // return 뒤로 html 작성 *주석처리 안됨*
    return <div> <Header/> 메인페이지 {name} <Footer /> </div>
} // func end
export default Component2

// [2] 헤더페이지
function Header (props) {
    return <div> 헤더 메뉴 </div>
} // func end

// [3] 푸터페이지
function Footer (props) {
    return <div> 푸터 메뉴 </div>
} // func end

