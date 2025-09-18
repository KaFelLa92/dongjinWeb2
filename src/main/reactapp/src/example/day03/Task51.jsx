// TASK5 : 기존 TASK4.jsx 이어 useEffect/axios를 활용해서 spring+mybatis 서버 와 통신하여 TASK5 완성(등록/전체조회/삭제)하시오.

import axios from "axios";
import { useEffect, useState } from "react";

export default function Task51( props ){
    const [ name , setName ] = useState('')
    const [ phone , setPhone ] = useState('')
    const [ age , setAge ] = useState('')
    const [ members , setMembers ] = useState( [ ] );

    // [1] 등록
    const memberAdd = async ( ) => {
        const obj = { name , phone , age }
        const response = await axios.post("http://localhost:8080/members" , obj)
        setMembers([...members , response.data])
        // 전체조회 초기화
        memberPrint()
        // 입력값 초기화
        setName('')
        setPhone('')
        setAge('')
    }

    // [2] 전체조회
    const memberPrint = async ( ) => {
        const response = await axios.get("http://localhost:8080/members")
        console.log(response)
        setMembers(response.data);
    }

    // [*] 최초 출력함수
    useEffect( () => { memberPrint()} , [])

    // [3] 삭제
    const memberDelete = async ( mno )=>{ console.log( mno );
        const response = await axios.delete("http://localhost:8080/members?mno=" + mno)
        console.log(response.data)
        const newMembers = members.filter( (m)=> { return m.mno != mno ; })
        setMembers( [...newMembers ] );
    }

    return (<>
        성명 : <input value={ name } onChange={ (e)=>{ setName(e.target.value ) } } />
        연락처 : <input value={ phone } onChange={ (e)=>{ setPhone( e.target.value) } }/>
        나이 : <input value={ age } onChange={ (e)=>{ setAge( e.target.value ) } } />
        <button onClick={ memberAdd }> 등록 </button> <br/>
        {   members.map( ( m ) => {
                return <div>
                        { m.name } { m.phone } { m.age }
                        <button onClick={ ()=> { memberDelete( m.mno ) }  }> 삭제 </button>
                    </div>
            })
        }
    </>)
}
