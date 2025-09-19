import axios from "axios";
import { useEffect, useState } from "react"
import './Eval6.css'

export default function Eval6(props) {

    // [*] 영화, 토론 입력값 관리할 useState
    const [cineInput, setCineInput] = useState({ ctitle: "", cdirector: "", cgenre: "", ccontent: "", cpwd: "" });
    const [agoraInput, setAgoraInput] = useState({ cno: "", acontent: "", apwd: "" })

    // [*] 전체조회 데이터 확인 useState
    const [cineList, setCineList] = useState([])
    const [agoraList, setAgoraList] = useState([])

    // [*] 최초 컴포넌트 실행 시 출력함수 실행 useEffect
    useEffect(() => { getAllCine() }, [])
    useEffect(() => { getAllAgora() }, [])

    // [1] 영화 등록
    /*
        익명으로 영화 추천 가능
        영화 제목, 감독, 장르, 간단한 소개 입력
        등록 시 삭제를 위한 비밀번호 설정
    */
    const addCine = async () => {
        // 비번 공란이면 기본 0000으로 설정
        const cineData = {
            ...cineInput,
            cpwd: cineInput.cpwd.trim() == "" ? "0000" : cineInput.cpwd
        }

        await axios.post("http://localhost:8080/eval6/cine", cineData)
        getAllCine(); // 전체조회 초기화
        setCineInput({ ctitle: "", cdirector: "", cgenre: "", ccontent: "", cpwd: "0000" })
    }

    // [2] 영화 삭제
    /*
        사용자가 등록한 비밀번호를 입력해야 삭제 가능
    */
    const deleteCine = async (cno) => {
        const password = prompt("삭제를 위한 비밀번호를 입력하세요.")
        if (password == null) {
            return;
        }
        if (password == "") {
            alert('비밀번호를 입력하세요.')
            return;
        }
        const response = await axios.delete("http://localhost:8080/eval6/cine?cpwd=" + password + "&cno=" + cno)
        if (response.data == 1) {
            alert('삭제되었습니다.')
            getAllCine(); // 전체조회 초기화
        } else {
            alert('비밀번호가 일치하지 않습니다.')
        }
        console.log(response.data);
    }

    // [3] 영화 목록 조회
    /*
        등록된 추천 영화 리스트 조회 가능
    */
    const getAllCine = async () => {
        const response = await axios.get("http://localhost:8080/eval6/cine")
        setCineList(response.data)
    }

    // [4] 토론 글 작성
    /*
        익명으로 해당 영화에 대한 토론 글 작성 가능
        비밀번호 설정하여 삭제 가능
    */
    const addAgora = async (cno) => {

        // 비번 공란이면 기본 0000으로 설정
        const agoraData = {
            ...agoraInput,
            cno,
            apwd: agoraInput.apwd.trim() == "" ? "0000" : agoraInput.apwd
        };
        const response = await axios.post("http://localhost:8080/eval6/agora", agoraData)
        console.log(response.data)
        getAllAgora();
        setAgoraInput({ cno: "", acontent: "", apwd: "" })
    }

    // [5] 토론 글 삭제
    /*
        사용자가 등록한 비밀번호 입력 후 삭제 가능
    */
    const deleteAgora = async (ano) => {
        const password = prompt("삭제를 위한 비밀번호를 입력하세요.")
        if (password == null) {
            return;
        }
        if (password == "") {
            alert('비밀번호를 입력하세요.')
            return;
        }
        const response = await axios.delete("http://localhost:8080/eval6/agora?apwd=" + password + "&ano=" + ano)
        if (response.data == 1) {
            alert('삭제되었습니다.')
            getAllAgora(); // 전체조회 초기화
        } else {
            alert('비밀번호가 일치하지 않습니다.')
        }
        console.log(response.data);
    }

    // [6] 영화별 토론 전체 조회
    /*
        특정 영화에 작성된 모든 토론 글을 조회 가능(cno 참조)
    */
    const getAllAgora = async () => {
        const response = await axios.get("http://localhost:8080/eval6/agora")
        setAgoraList(response.data)
    }

    return (<>
        <div id="container">
            <h2> 영화광들의 토론장. 시네아고라입니다. </h2>
            <h4> 토론할 영화 등록하기 </h4>
            <div className="space"> 영화명 : </div>
            <input placeholder="영화명" name="ctitle" value={cineInput.ctitle}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key == 'Enter' && addCine()} />
            <div className="space"> 감독 : </div>
            <input placeholder="감독" name="cdirector" value={cineInput.cdirector}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key == 'Enter' && addCine()} />
            <div className="space"> 장르 : </div>
            <input placeholder="장르" name="cgenre" value={cineInput.cgenre}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key == 'Enter' && addCine()} />
            <div className="space"> 비밀번호 : </div>
            <input placeholder="비밀번호" type="password" name="cpwd" value={cineInput.cpwd}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key == 'Enter' && addCine()} />
           <div className="space"> 소개 : </div>
            <textarea placeholder="영화 소개를 작성해주세요" name="ccontent" value={cineInput.ccontent}
                onChange={(e) => setCineInput({ ...cineInput, [e.target.name]: e.target.value })}
                onKeyDown={(e) => e.key == 'Enter' && addCine()}></textarea>

            <button onClick={addCine}> 영화 등록하기 </button>

            {cineList.map((c) => {
                // [*] 토론내용 넘버 매기기 
                let index = 1;
                return (<>
                    <div id="cineContainer">
                        <h4> {c.ctitle} </h4>
                        <div className="citiDiv">
                            <ul key={c.cno} className="cineUl">
                                <li> 감독 : {c.cdirector} </li>
                                <li> 장르 : {c.cgenre} </li>
                                <li> 소개 : {c.ccontent} </li>
                                <button id="deleteBtn" onClick={() => deleteCine(c.cno)}> 삭제 </button>
                            </ul>
                            <h5> 토론 등록하기 </h5>
                            <ul>
                                <li> <div className="space"> 토론 내용 : </div> <textarea placeholder="토론 내용을 작성해주세요." name="acontent" value={agoraInput.acontent}
                                    onChange={(e) => setAgoraInput({ ...agoraInput, [e.target.name]: e.target.value })}
                                    onKeyDown={(e) => e.key == 'Enter' && addAgora(c.cno)}></textarea> </li>
                                <li> <div className="space"> 비밀번호 : </div> <input placeholder="비밀번호" name="apwd" value={agoraInput.apwd}
                                    onChange={(e) => setAgoraInput({ ...agoraInput, [e.target.name]: e.target.value })}
                                    onKeyDown={(e) => e.key == 'Enter' && addAgora(c.cno)} /> </li>
                                <button onClick={() => addAgora(c.cno)}>토론 등록하기 </button>
                            </ul>
                        </div>
                    </div>
                    <h3 className="agoraTitle"> 토론 내용 </h3>
                    {agoraList.filter((a) => a.cno == c.cno)
                        .map((a) => (
                            <div id="agoraContainer">
                                <div>
                                    <ul key={a.ano}>
                                        <li> 번호 : {index++} </li>
                                        <li> {a.acontent} </li>
                                        <button id="deleteBtn" onClick={() => deleteAgora(a.ano)}> 삭제 </button>
                                    </ul>
                                </div>
                            </div>
                        ))}
                </>)

            })}


        </div>
    </>)

}