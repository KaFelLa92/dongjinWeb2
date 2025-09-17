import { useState } from 'react';
import './Task4.css'

export default function Task4(props) {

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
        if(!data.name || !data.number || !data.age){
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

    // [5] 배열 삭제하기
    const deleteArray = (no) => {
        // no가 일치할 경우 filter로 삭제
        setInfomation(infomation.filter((info) => info.no !== no))
    }

    // [6] 리턴
    return (<>
        <div id="container">
            <h2> 전화번호부 </h2>
            <input placeholder="성명" name='name' value={data.name} onChange={addData} />
            <input placeholder="연락처(예: 010-1234-5678)" name='number' value={data.number} onChange={addData} />
            <input placeholder="나이" name='age' value={data.age} onChange={addData} />
            <button onClick={addArray}> 등록 </button>

            {infomation.map((info) => {
                return (<>
                    <ul>
                        <li> <span>성명</span>: {info.name} </li>
                        <li> <span>연락처</span>: {info.number} </li>
                        <li> <span>나이</span>: {info.age}</li>
                        <button onClick={() => deleteArray(info.no)}> 삭제 </button>
                    </ul>
                </>)
            })}
            총 {infomation.length}명
        </div>
    </>)

}