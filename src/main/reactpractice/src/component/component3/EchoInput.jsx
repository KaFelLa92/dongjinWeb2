import { useState } from "react";

export default function EchoInput() {
    // 인풋 입력 값 관리를 위한 useState
    const [text, setText] = useState('');
    // input 내용 변경마다 호출되는 함수
    const handleChange = (e) => {
        setText(e.target.value);
    }

    return (<>
        <div>
            <input type="text" value={text} onChange={handleChange} placeholder="입력해보셈" />
            <p> 입력한 내용 : {text} </p>
        </div>
    </>)
}