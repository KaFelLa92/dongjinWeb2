/*
    [web1] 백틱 탬플릿 : 키보드 [tab] 위에 `(백틱) 기호 이용한 JS코드 연결 방법
            예] let name = "유재석"
            `<div> ${ name } </div>`
    [web2] JSX 템플릿 : 리액트 내 자동 포함
            예] let name = "유재석"
            return <div> { name } </div>

*/

// [1] 컴포넌트 생성
export default function Component3(props) {
    // -----> JS 코드 Start
    let name = "유재석";

    // <------ JS 코드 End : return 전까지 * return부터는 JSX 문법
    // 함수 반환값은 1개라서, <div> 두 개를 연달아 쓸 수 없음 (div 값을 두 개 반환하는 것으로 인식함)
    // 코드 두 줄 이상이면 return (<> </>) 세팅하고 할 것
    return (<>
        { /* 주석 처리 */}
        <div>
            {name}님 방가방가 쌉싸리와용 *^^*
        </div>
        <div>
            {13 + 20}은 34일까요?
        </div>
        {/* 다른 컴포넌트 포함하면서 속성(props)/자료 전달 */}
        <input value = "유재석" />
        <SubCom1 key1 = "value1" key2 = "40" />
        <SubCom1 key1 = "유재석" key2 = "60" /> { /* 이 컴포넌트 사용 */ }
    </>);

    // <------ JSX 코드 End
} // func end

// [2] 컴포넌트/함수 선언2
function SubCom1(props) {

    const obj = { name: "강호동", age: 50 } // JS
    console.log(obj);
    // 2-1 : props 확인
    console.log(props); /* Object Key1 : "value1" key2 : "40" [[Prototype]] : Object */
    // 2-2 : props (배열/객체) 구조 분해
    const { key1 , key2 } = props; 
    // vs const key1 = props.key1; const key2 = props.key2;

    // JSX 문법
    return (<>
        <h4> {obj.name} 님의 나이는 {obj.age} 입니다. </h4>
        <h4> {props.key1} 님의 나이 : {props.key2} </h4>
        <h4> {key1} 님의 나이 : {key2} </h4>
    </>)

} // func end