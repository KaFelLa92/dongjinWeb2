# `Eval6.jsx` 개선 방안

안녕하세요! `Eval6.jsx`에서 영화별 토론이 나오지 않는 문제에 대한 개선 방안을 안내해 드립니다.

## 문제 원인 분석

현재 코드의 문제는 **토론 데이터를 가져오는 방식**에 있습니다.

1.  `useEffect(() => { getAllAgora() }, [])` 구문은 컴포넌트가 처음 로드될 때 `getAllAgora` 함수를 호출합니다.
2.  하지만 `getAllAgora` 함수는 `async (cno) => { ... }` 형태로, 특정 영화의 `cno`를 파라미터로 받아 해당 영화의 토론만 가져오도록 설계되어 있습니다.
3.  `useEffect`에서 파라미터 없이 호출했기 때문에 `cno`는 `undefined`가 되고, `axios.get("http://localhost:8080/eval6/agora?cno=undefined")` 와 같이 잘못된 요청이 보내져 토론 목록(`agoraList`)이 비어있게 됩니다.
4.  `cineList`를 순회하며 `agoraList.filter(...)`를 사용해 토론을 표시하는 렌더링 로직은 `agoraList`에 모든 토론 데이터가 들어있을 것을 기대하지만, 실제로는 비어있으므로 아무것도 출력되지 않습니다.

## 해결 방안

컴포넌트가 로드될 때 **모든 영화 목록**과 **모든 토론 목록**을 각각 가져와서 상태에 저장한 뒤, 화면에 렌더링할 때 영화와 토론을 연결해주는 방식으로 수정해야 합니다.

백엔드에서 `http://localhost:8080/eval6/agora` 주소로 GET 요청 시 모든 토론 데이터를 반환한다고 가정하고 아래와 같이 코드를 개선할 수 있습니다.

## 개선된 코드 (`Eval6.jsx`)

아래는 문제점을 수정한 전체 코드입니다. 기존 `Eval6.jsx` 파일의 내용을 이 코드로 대체하시면 됩니다.

```jsx
import axios from "axios";
import { useEffect, useState } from "react";

export default function Eval6(props) {

    // [*] 영화, 토론 입력값 관리할 useState
    const [cineInput, setCineInput] = useState({ ctitle: "그 제목", cdirector: "그 감독", cgenre: "그 장르", ccontent: "", cpwd: "0000" });
    const [agoraInput, setAgoraInput] = useState({ cno: "", acontent: "", apwd: "0000" });

    // [*] 전체조회 데이터 확인 useState
    const [cineList, setCineList] = useState([]);
    const [agoraList, setAgoraList] = useState([]);

    // [3] 영화 목록 전체 조회
    const fetchAllCine = async () => {
        try {
            const response = await axios.get("http://localhost:8080/eval6/cine");
            setCineList(response.data);
        } catch (error) {
            console.error("영화 목록을 불러오는 중 오류가 발생했습니다:", error);
        }
    };

    // [6] 토론 전체 조회
    // 기존의 cno를 받던 getAllAgora에서 모든 토론을 가져오는 함수로 변경합니다.
    // 백엔드 API가 파라미터 없이 /eval6/agora를 호출하면 모든 토론을 반환해야 합니다.
    const fetchAllAgora = async () => {
        try {
            const response = await axios.get("http://localhost:8080/eval6/agora");
            setAgoraList(response.data);
        } catch (error) {
            console.error("토론 목록을 불러오는 중 오류가 발생했습니다:", error);
        }
    };

    // [*] 최초 컴포넌트 실행 시 영화와 토론 데이터를 모두 가져옵니다.
    useEffect(() => {
        fetchAllCine();
        fetchAllAgora();
    }, []);

    // [1] 영화 등록
    const addCine = async () => {
        // 기능 구현
    };

    // [2] 영화 삭제
    const deleteCine = async (cpwd, cno) => {
        // 기능 구현
    };

    // [4] 토론 글 작성
    const addAgora = async () => {
        // 기능 구현
    };

    // [5] 토론 글 삭제
    const deleteAgora = async (apwd, ano) => {
        // 기능 구현
    };

    return (
        <div id="container">
            <h2> 영화 및 토론 목록 </h2>

            {cineList.map((c) => (
                <div key={c.cno} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px' }}>
                    <ul>
                        <li> 영화명 : {c.ctitle} </li>
                        <li> 감독 : {c.cdirector} </li>
                        <li> 장르 : {c.cgenre} </li>
                        <li> 소개 : {c.ccontent} </li>
                        <button id="deleteBtn" onClick={() => deleteCine(c.cpwd, c.cno)}> 영화 삭제 </button>
                    </ul>
                    
                    <h4>&nbsp; &gt; 토론의 장</h4>
                    {agoraList
                        .filter((a) => a.cno === c.cno)
                        .map((a) => (
                            <ul key={a.ano} style={{ marginLeft: '20px', borderLeft: '2px solid #eee', paddingLeft: '10px' }}>
                                <li> {a.acontent} </li>
                                <button id="deleteBtn" onClick={() => deleteAgora(a.apwd, a.ano)}> 토론 삭제 </button>
                            </ul>
                        ))}
                </div>
            ))}
        </div>
    );
}
```

### 주요 변경 사항

1.  **데이터 조회 함수 분리 및 명확화**:
    *   `getAllCine` -> `fetchAllCine`: 모든 영화를 가져오는 함수.
    *   `getAllAgora` -> `fetchAllAgora`: **모든 토론**을 가져오는 함수로 변경. 더 이상 `cno` 파라미터를 받지 않고, `http://localhost:8080/eval6/agora`로 요청을 보냅니다.
2.  **`useEffect` 통합**:
    *   두 개의 `useEffect`를 하나로 합쳐 컴포넌트가 처음 마운트될 때 `fetchAllCine()`과 `fetchAllAgora()`를 모두 호출하도록 변경했습니다. 이렇게 하면 초기에 필요한 모든 데이터를 한 번에 불러올 수 있습니다.
3.  **렌더링 구조 개선**:
    *   `map` 함수 사용 시 각 요소에 고유한 `key` prop을 추가하여 React의 렌더링 성능을 최적화했습니다. (`<div key={c.cno}>`, `<ul key={a.ano}>`)
    *   가독성을 위해 약간의 스타일을 추가했습니다.

이와 같이 수정하시면 영화 목록과 함께 각 영화에 해당하는 토론이 정상적으로 출력될 것입니다.
