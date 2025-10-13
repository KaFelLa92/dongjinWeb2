import Button from "@mui/joy/Button";
import axios from "axios";
import { useRef } from "react";

export default function Component15(props) {

    // [1]
    const axios1 = async () => {
        try {
            const response = await axios.get("http://localhost:8080/axios");
            const data = response.data;
            console.log("[1] : " + response.data);
        } catch (e) {
            console.log(e);
        }
    }

    // [2] 로그인 , axios.post( url , body , option )
    const axios2 = async () => {
        try {
            const obj = { id: "qwe", password: "1234" }
            const option = { withCredentials: true } // Spring에서 크레덴셜스 받아오기
            const response = await axios.post("http://localhost:8080/axios/login", obj, option)
            const data = response.data;
            console.log("[2] : " + data);
        } catch (e) {
            console.log(e);
        }
    }

    // [3] 내정보(로그인 후 열람 가능) , axios.get( url , option )
    const axios3 = async () => {
        try {
            const option = { withCredentials: true }
            const response = await axios.get("http://localhost:8080/axios/info", option)
            const data = response.data;
            console.log("[3] : " + data);
        } catch (e) {
            console.log(e);
        }
    }

    // axios는 기본 전송 타입이 json(fetch는 form)
    // [4] 일반 폼 : 폼 전송 시 자바의 속성 매핑은 form 안에 name 속성으로 매핑
    const formRef1 = useRef();
    const axios4 = async () => {
        try{
            const form = formRef1.current; // 4-1 : useRef 참조 중인 dom객체 가져오기
            const option = { headers : { "Content-Type" : "application/x-www-form-urlencoded" } }
            const response = await axios.post("http://localhost:8080/axios/form" , form , option);
            const data = response.data;
            console.log("[4] : " + data)
        } catch(e){
            console.log(e);
        }
    }

    // [5] 첨부파일 폼 
    const formRef2 = useRef();
    const axios5 = async () => {
        try {
            const form = formRef2.current;
            const formData = new FormData( form ); // 폼 데이터를 바이너리(바이트) 형식으로 변환
            const option = { headers : {"Content-Type" : "multipart/form-data"} }
            const response = await axios.post("http://localhost:8080/axios/formdata" , formData , option);
            const data = response.data;
            console.log("[5] : " + data);
        } catch(e){
            console.log(e)
        }
    }




    return (<>
        <h3> AXIOS 테스트 </h3>
        <Button onClick={axios1}> axios1 </Button>
        <Button onClick={axios2}> axios2 </Button>
        <Button onClick={axios3}> axios3 </Button>
        <form ref={formRef1}>
            <input name="id" />
            <input name="password" />
            <Button onClick={axios4} type="button"> axios4 </Button>
        </form>
        <form ref={formRef2}>
            <input type="file" name="file" />
            <Button onClick={axios5} type="button"> axios5 </Button>
        </form>
    </>)
}