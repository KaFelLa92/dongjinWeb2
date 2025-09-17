export default function Component4(props) {

    // JS 구역
    const obj = {
        name : '유재석' ,
        age : 40
    }

    return (<>

        {/* JSX 구역 : HTML과 JS의 혼합 */}
        <div>
            <h3> {obj.name} , {obj.age} </h3>
            <SubComp key1 = "value" key2 = "value"> </SubComp>
            <SubComp name = "유재석" age = "40"> </SubComp>
            <SubComp name = {obj.name} age = {obj.age} ></SubComp>
        </div>
    </>)
}

function SubComp( props ) {
    console.log(props)
    return (<>
        <h3> 자식 컴포넌트 </h3>
        <SubSubComp key3 = "value"> </SubSubComp>
    
    </>)
}

let count2 = 0; // 전역변수 증가 시키기 (세 버튼 어떤 걸 눌러도 증가됨. 공유됨)

function SubSubComp ( props ){
    console.log(props)

    let count = 0; // 지역변수 특성 : 현재 실행 중인 함수(컴포넌트) 안에서 사용되는 변수
    const onAdd = () => { 
        count++;
        console.log(`지역변수 : ${count}`)
        count2++;
        console.log( `전역변수 : ${count2}`)
    }

    return(<>
        <h6> 자손 컴포넌트 </h6> {/* 세 번 나오는 이유 => 부모가 자식을 3번 호출했기 때문에, 자손도 3번 호출됨 */}
        <button onClick={ onAdd }> 버튼 </button>
        <h6> 지역변수 count : { count } / 전역변수 count2 : {count2} </h6>
    </>)
}