// TASK5 : 기존 TASK4.jsx 이어 useEffect/axios를 활용해서 spring+mybatis 서버 와 통신하여 TASK5 완성(등록/전체조회/삭제)하시오.

import axios from "axios"
import { useEffect, useState } from "react"

export default function Task5(props) {

    // [*] 입력값 관리할 useState
    const [input, setInput] = useState({ name: "", phone: "", age: "" });

    // [1] 등록
    const memberAdd = async () => {
        const response = await axios.post("http://localhost:8080/members", input) // 인풋에 넣은 값을 서버로 전송
        console.log(response.data)
        console.log(response.status)
        memberPrint(); // 전체조회 초기화
        setInput({ name: "", phone: "", age: "" }); // 등록값 초기화
    }

    // [*] 전체조회 데이터 확인 useState
    const [list, setList] = useState([])

    // [2] 전체조회
    const memberPrint = async () => {
        const response = await axios.get("http://localhost:8080/members")
        console.log(response)
        console.log(response.data)
        console.log(response.status)
        setList(response.data)
    }

    // [*] 최초 컴포넌트 실행 시 출력함수 실행
    useEffect(() => { memberPrint() }, [])

    // [3] 삭제
    const memberDelete = async (mno) => {
        const response = await axios.delete("http://localhost:8080/members?mno=" + mno)
        console.log(response.data)
        console.log(response.status)
        memberPrint()
    }

    return (<>
        <div id="container">
            <h2> 전화번호부 </h2>
            <div id="inputBox">
                <input placeholder="성명" name="name" value={input.name} 
                onChange={(e) => setInput({ ...input, [e.target.name]: e.target.value })} 
                onKeyDown={(e) => e.key == 'Enter' && memberAdd()} />

                <input placeholder="연락처(예: 010-1234-5678)" name="phone" value={input.phone} 
                onChange={(e) => setInput({ ...input, [e.target.name]: e.target.value })} 
                onKeyDown={(e) => e.key == 'Enter' && memberAdd()} />

                <input placeholder="나이" name="age" value={input.age} 
                onChange={(e) => setInput({ ...input, [e.target.name]: e.target.value })} 
                onKeyDown={(e) => e.key == 'Enter' && memberAdd()} />

                <button onClick={memberAdd}> 등록 </button>
            </div>
            {list.map((m) => {
                return (<>
                    <div id="infoBox">
                        <ul>
                            <li> <span class="bold">성명</span>: {m.name} </li>
                            <li> <span class="bold">연락처</span>: {m.phone} </li>
                            <li> <span class="bold">나이</span>: {m.age}</li>
                        </ul>
                        <button id="deleteBtn" onClick={() => memberDelete(m.mno)}> 삭제 </button>
                    </div>
                </>)
            })}
            <span class="sum"> 총 {list.length}명 </span>
        </div>
    </>)
}
