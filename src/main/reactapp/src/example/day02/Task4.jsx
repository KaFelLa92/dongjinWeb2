import { useState } from 'react';
import './Task4.css'

export default function Task4(props) {

    // 1. useState : 상태관리 훅(특정한 상태가 변경되면 재렌더링해줌)
    /*
        const [ name , setName ] = useState('') 
        const [ phone , setPhone ] = useState('') 
        const [ age , setAge ] = useState('') 
        const [ members , setMembers ] = useState([ ]) // 회원정보 객체를 담는 리스트
    */


    // [1] 기본 배열 useState (삭제 함수를 위해 보이지 않는 no값 책정 )
    const [infomation, setInfomation] = useState([
        { no: 1, name: "신동엽", number: "010-7894-7894", age: "50" },
        { no: 2, name: "강호동", number: "010-4321-4321", age: "40" },
        { no: 3, name: "유재석", number: "010-1234-1234", age: "30" }
    ])

    // [2] no 값 state로 구하기
    const [nextNo, setNextNo] = useState(4);

    // [3] useState로 인풋 밸류 관리하기
    const [data, setData] = useState({ name: '', number: '', age: '' });
    const addData = (e) => {
        const { name, value } = e.target;
        setData({ ...data, [name]: value })
    }

    // [4] 배열 추가하기
    const addArray = () => {
        // 간단 유효성 검사
        if (!data.name || !data.number || !data.age) {
            alert("성명, 연락처, 나이를 모두 입력해주세요.")
            return;
        }
        const newData = {
            no: nextNo,
            ...data
        }
        setInfomation([...infomation, newData]);
        setData({ name: '', number: '', age: '' }) // 입력창 초기화
        setNextNo(nextNo + 1);
    }

    /*
    const onAdd = ( ) => {
        const obj = { name , phone , age } // 입력받은 데이터 객체화
        members.push( obj ) // 객체를 리스트에 저장   
        setMembers( [...members] ) // 리스트를 재렌더링 
        // ( 주의할점 : 객체/배열은 ...스프레드 연산자 이용한 복사 = 주소값 변경 )
    }
    */

    // [5] 배열 삭제하기 , 무엇을 삭제할지 매개변수 필요(pk/중복값 없는)
    // * 연락처 또한 중복이 없으니, 연락처를 PK로 삼아도 됨! 콘셉트는 각자 정하기 가능
    const deleteArray = (no) => {
        // no가 일치할 경우 filter로 삭제
        setInfomation(infomation.filter((info) => info.no !== no))
    }

    /*
    const onDelete = { deletePhone } => { console.log(deletePhone);
        // 반복문 이용하여 리스트내 삭제할 번호 찾아서 제거
        const newMembers = members.filter( (m) => { return m.phone != deletePhone ; })
        // 수정된 리스트 재렌더링
        setMembers( [...newMembers] );
    }
    */

    /*
        jsx에서 { } 중괄호 js 표현식의 시작과 끝
        [1] 1. onClick = { 함수명 } 또는 
            2. onClick = { () => { } } (이건 함수 '정의'임)
            3. 주의할점 : onClick{ 함수명() } 뒤에 소괄호 넣지 마라 (함수 실행됨. 이후 재사용 불가하고 불변)
        [2] 리스트 출력시 forEach 대신 *map* 사용
    */
    // [6] 리턴
    return (<>
        <div id="container">
            <h2> 전화번호부 </h2>
            <div id="inputBox">
                <input placeholder="성명" name='name' value={data.name} onChange={addData} onKeyDown={(e) => e.key == 'Enter' && addArray()} />
                <input placeholder="연락처(예: 010-1234-5678)" name='number' value={data.number} onChange={addData} onKeyDown={(e) => e.key == 'Enter' && addArray()} />
                <input placeholder="나이" name='age' value={data.age} onChange={addData} onKeyDown={(e) => e.key == 'Enter' && addArray()} />
                <button onClick={addArray}> 등록 </button>
            </div>
            {infomation.map((info) => {
                return (<>
                    <div id="infoBox">
                        <ul>
                            <li> <span class="bold">성명</span>: {info.name} </li>
                            <li> <span class="bold">연락처</span>: {info.number} </li>
                            <li> <span class="bold">나이</span>: {info.age}</li>
                        </ul>
                        <button id="deleteBtn" onClick={() => deleteArray(info.no)}> 삭제 </button>
                    </div>
                </>)
            })}
            <span class="sum"> 총 {infomation.length}명 </span>
        </div>
    </>)

}