import axios from "axios"
import { useEffect, useState } from "react"

export default function Component10(props) {

    // * 입력받은 데이터 관리하는 useState
    const [bcontent, setBcontent] = useState('')
    const [bwriter, setBwriter] = useState('')

    // [1] 등록 함수
    const boardWrite = async () => {
        const obj = { bcontent, bwriter }
        const response = await axios.post("http://localhost:8080/board", obj)
        console.log(response.data)
        console.log(response)
        // 전체조회함수 호출
        boardPrint();
        setBcontent('')
        setBwriter('')
    }

    // * 전체 조회 데이터 확인을 위한 useState
    const [list, setList] = useState([{ bno: 1, bcontent: '테스트', bwriter: '테스트' }]);

    // [2] 전체조회 함수
    const boardPrint = async () => {
        // axios 요청
        const response = await axios.get("http://localhost:8080/board")
        // axios 요청 값을 setState 이용해 재렌더링
        setList(response.data)
    }

    // [3] useEffect 이용한 *최초 컴포넌트* 실행 시 출력함수 실행
    useEffect(() => { boardPrint() }, [])

    // [4] 삭제 함수
    const boardDelete = async (bno) => {
        // axios : 백엔드 삭제
        const response = await axios.delete("http://localhost:8080/board?bno="+bno)
        console.log(response.data)
        // 삭제할 bno를 매개변수로 받아, 새로운 리스트 만들기 : 프론트 삭제
        const newList = list.filter((b) => { return b.bno !== bno })
        /*
        const newList = []                      // 새로운 배열 선언
        for (let i = 0; i < list.length; i++){  // 기존 배열 내 인덱스를 순회
            if (list[i].bno != bno){            // i번째 bno와 삭제할 bno와 같지 않으면
                newList.push(list[i]);          // 새로운 배열에 대입
            }
        }
        */
        // 재렌더링
        setList([...newList])
    }

    return (<>

        <h3> 스프링 서버의 boardService13(day07) 통신</h3>
        <input value={bcontent} onChange={(e) => { setBcontent(e.target.value) }} placeholder="작성자" className="bwriter" />
        <input value={bwriter} onChange={(e) => { setBwriter(e.target.value) }} placeholder="본문" className="bcontent" />
        <button onClick={boardWrite}> 게시물 등록 </button>
        {
            list.map((b) => {
                return <ul className="listTable">
                    <li>{b.bno} </li>
                    <li>{b.bcontent} </li>
                    <li>{b.bwriter} </li>
                    <button id="deleteBtn" onClick={() => boardDelete(b.bno)}>
                        삭제
                    </button>
                </ul>
            })
        }
    </>)
}