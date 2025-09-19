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