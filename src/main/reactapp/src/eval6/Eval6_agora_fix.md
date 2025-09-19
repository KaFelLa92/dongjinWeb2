# `Eval6.jsx`: 토론 등록 기능 개선 방안

안녕하세요. '토론 등록하기' 기능이 각 영화에 맞게 동작하도록 `Eval6.jsx` 파일을 개선하는 방안을 안내해 드립니다.

## 문제 원인 분석

현재 코드에서는 모든 '토론 등록하기' 입력 필드(내용, 비밀번호)가 단 하나의 상태(`agoraInput`)를 공유하고 있습니다. 이로 인해 두 가지 문제가 발생합니다.

1.  **입력 상태 공유**: 한 영화의 토론 입력란에 내용을 적으면 다른 모든 영화의 입력란에도 똑같은 내용이 표시됩니다.
2.  **영화 식별 불가**: '토론 등록하기' 버튼을 눌렀을 때, `addAgora` 함수는 어떤 영화(`cno`)에 대한 토론을 등록해야 하는지 알 수 없습니다.

## 해결 방안

이 문제를 해결하기 위해, 각 영화별로 토론 입력 상태를 독립적으로 관리하고, 토론을 등록할 때 해당 영화의 `cno`를 명확하게 전달하도록 코드를 수정해야 합니다.

1.  **독립적인 입력 상태 관리**:
    *   기존의 `agoraInput` 상태를 `newAgoraInputs` 라는 새로운 상태로 교체합니다. 이 상태는 객체(`{}`) 형태이며, 영화의 `cno`를 `key`로 사용하여 각 영화의 토론 입력값(`{acontent: '...', apwd: '...'}`)을 독립적으로 저장합니다.
2.  **입력 처리 핸들러 추가**:
    *   `handleAgoraInputChange` 라는 새로운 함수를 만듭니다. 이 함수는 `cno`, 필드명(`acontent` 또는 `apwd`), 그리고 입력값을 받아 `newAgoraInputs` 상태를 올바르게 업데이트합니다.
3.  **`addAgora` 함수 수정**:
    *   `addAgora` 함수가 `cno`를 파라미터로 받도록 변경합니다.
    *   함수 내부에서는 전달받은 `cno`를 사용해 `newAgoraInputs` 상태에서 해당 영화의 토론 내용과 비밀번호를 가져옵니다.
    *   서버에 POST 요청을 보낼 때, 이 데이터와 `cno`를 함께 전송합니다.
    *   등록이 완료되면 해당 `cno`에 해당하는 입력 필드만 초기화합니다.
4.  **JSX 수정**:
    *   '토론 등록하기' 섹션의 `textarea`와 `input`이 `newAgoraInputs` 상태와 `handleAgoraInputChange` 핸들러를 사용하도록 `value`와 `onChange` 속성을 수정합니다.
    *   '토론 등록하기' 버튼의 `onClick` 이벤트가 `addAgora` 함수를 호출할 때 현재 영화의 `c.cno`를 인자로 전달하도록 수정합니다.

## 개선된 전체 코드 (`Eval6.jsx`)

아래 코드를 복사하여 기존 `Eval6.jsx` 파일의 전체 내용을 대체하시면 됩니다.

```jsx
import axios from "axios";
import { useEffect, useState } from "react";
import './Eval6.css';

export default function Eval6(props) {

    // [*] 영화 입력값 관리
    const [cineInput, setCineInput] = useState({ ctitle: "", cdirector: "", cgenre: "", ccontent: "", cpwd: "0000" });

    // [*] 영화별 '토론 등록' 입력값 관리
    // cno를 key로 사용하여 각 영화의 토론 입력값을 독립적으로 관리합니다.
    const [newAgoraInputs, setNewAgoraInputs] = useState({});

    // [*] 전체조회 데이터
    const [cineList, setCineList] = useState([]);
    const [agoraList, setAgoraList] = useState([]);

    // [*] 데이터 조회 함수
    const getAllCine = async () => {
        const response = await axios.get("http://localhost:8080/eval6/cine");
        setCineList(response.data);
    };

    const getAllAgora = async () => {
        const response = await axios.get("http://localhost:8080/eval6/agora");
        setAgoraList(response.data);
    };

    // [*] 최초 실행
    useEffect(() => {
        getAllCine();
        getAllAgora();
    }, []);

    // [1] 영화 등록
    const addCine = async () => {
        await axios.post("http://localhost:8080/eval6/cine", cineInput);
        getAllCine();
        setCineInput({ ctitle: "", cdirector: "", cgenre: "", ccontent: "", cpwd: "0000" });
    };

    // [4] 토론 글 작성
    // cno를 인자로 받아 어떤 영화에 대한 토론인지 명시합니다.
    const addAgora = async (cno) => {
        // 해당 cno에 대한 입력값이 없으면 실행하지 않습니다.
        const agoraInputData = newAgoraInputs[cno];
        if (!agoraInputData || !agoraInputData.acontent) {
            alert("토론 내용을 입력해주세요.");
            return;
        }

        // 서버로 보낼 데이터 객체 생성 (cno 포함)
        const payload = {
            ...agoraInputData,
            cno: cno,
            apwd: agoraInputData.apwd || "0000" // 비밀번호가 없으면 기본값 설정
        };

        await axios.post("http://localhost:8080/eval6/agora", payload);
        
        // 토론 목록 새로고침
        getAllAgora();

        // 해당 영화의 토론 입력창만 초기화
        setNewAgoraInputs(prev => ({
            ...prev,
            [cno]: { acontent: "", apwd: "" }
        }));
    };

    // [*] 토론 입력창의 변경을 처리하는 함수
    const handleAgoraInputChange = (cno, field, value) => {
        setNewAgoraInputs(prev => ({
            ...prev,
            [cno]: {
                ...(prev[cno] || {}), // 기존 해당 cno의 객체가 없으면 빈 객체로 시작
                [field]: value
            }
        }));
    };


    // [2] 영화 삭제
    const deleteCine = async (cpwd, cno) => {
        // 기능 구현 필요
    };

    // [5] 토론 글 삭제
    const deleteAgora = async (apwd, ano) => {
        // 기능 구현 필요
    };

    return (
        <div id="container">
            <h2> 영화광들의 토론장. 시네아고라입니다. </h2>
            <h4> 토론할 영화 등록하기 </h4>
            영화명 :
            <input placeholder="영화명" name="ctitle" value={cineInput.ctitle}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key === 'Enter' && addCine()} />
            감독 :
            <input placeholder="감독" name="cdirector" value={cineInput.cdirector}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key === 'Enter' && addCine()} />
            장르 :
            <input placeholder="장르" name="cgenre" value={cineInput.cgenre}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key === 'Enter' && addCine()} />
            비밀번호 :
            <input placeholder="기본 비밀번호는 0000입니다." type="password" name="cpwd" value={cineInput.cpwd}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key === 'Enter' && addCine()} />
            소개 :
            <textarea placeholder="영화 소개를 작성해주세요" name="ccontent" value={cineInput.ccontent}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key === 'Enter' && addCine()}></textarea>

            <button onClick={addCine}> 영화 등록하기 </button>

            {cineList.map((c) => {
                let index = 1;
                return (
                    <div id="cineContainer" key={c.cno}>
                        <h4> {c.ctitle} </h4>
                        <div className="citiDiv">
                            <ul className="cineUl">
                                <li> 감독 : {c.cdirector} </li>
                                <li> 장르 : {c.cgenre} </li>
                                <li> 소개 : {c.ccontent} </li>
                                <button id="deleteBtn" onClick={() => deleteCine(c.cpwd, c.cno)}> 삭제 </button>
                            </ul>
                            <h5> 토론 등록하기 </h5>
                            <ul>
                                <li> 토론 내용 :
                                    <textarea
                                        placeholder="토론 내용을 작성해주세요."
                                        name="acontent"
                                        value={newAgoraInputs[c.cno]?.acontent || ''}
                                        onChange={(e) => handleAgoraInputChange(c.cno, 'acontent', e.target.value)}
                                    ></textarea>
                                </li>
                                <li> 비밀번호 :
                                    <input
                                        type="password"
                                        placeholder="기본 비밀번호는 0000입니다."
                                        name="apwd"
                                        value={newAgoraInputs[c.cno]?.apwd || ''}
                                        onChange={(e) => handleAgoraInputChange(c.cno, 'apwd', e.target.value)}
                                    />
                                </li>
                                <button onClick={() => addAgora(c.cno)}>토론 등록하기</button>
                            </ul>
                        </div>
                        <h3> 토론 내용 </h3>
                        {agoraList
                            .filter((a) => a.cno === c.cno)
                            .map((a) => (
                                <div id="agoraContainer" key={a.ano}>
                                    <div>
                                        <ul>
                                            <li> 번호 : {index++} </li>
                                            <li> {a.acontent} </li>
                                            <button id="deleteBtn" onClick={() => deleteAgora(a.apwd, a.ano)}> 삭제 </button>
                                        </ul>
                                    </div>
                                </div>
                            ))}
                    </div>
                );
            })}
        </div>
    );
}
```