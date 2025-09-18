import axios from 'axios';

export default function Component9 ( props ){
    
    // [1] axios : React.js 주로 사용되는 Rest API (JSON) 비동기 통신 함수 , fetch(js 내장)
    // [2] 사용법 : 
    //      1. 설치 (node.js) 라이브러리 추가
    //          (1) 리액트 서버 종료된 상태에서 
    //          (2) 리액트 폴더 통합 터미널 열기
    //          (3) npm install axios 또는 npm i axios
    //              * https://www.npmjs.com/ : 각종 npm 라이브러리 설치 코드 공유
    //          (4) 설치후 리액트 서버 재실행 : npm run dev
    //      2. 문법
    //          (1) import

    // [3-1] axios get test (https://jsonplaceholder.typicode.com/)
    const onAxios1 = async () => {
        
        const response1 = await axios.get("https://jsonplaceholder.typicode.com/posts")
        console.log(response1.status);   // HTTP 응답코드상태 200 (성공의미)
        console.log(response1.data)      // HTTP 응답자료
        if( response1.status == 200){
            console.log(response1.data)
        }

        const response2 = await axios.get("https://jsonplaceholder.typicode.com/comments?postId=1")
        console.log(response2.data);
    }

    // [3-2] axios post test 
    const onAxios2 = async () => {
        const obj = { title : "test" , body : "test" , userId : 3 }
        const response1 = await axios.post("https://jsonplaceholder.typicode.com/posts" , obj )
        console.log(response1.data)
    }

    // [3-3] axios put test
    const onAxios3 = async () => {
        const obj = { id : 3 ,title : "foo" , body : 'bar' , userId : 3 }
        const response1 = await axios.put("https://jsonplaceholder.typicode.com/posts/1" , obj)
        console.log(response1.data)
    }

    // [3-4] axios delete test
    const onAxios4 = async () => {
        const response1 = await axios.delete("https://jsonplaceholder.typicode.com/posts/1")
        console.log(response1.data)
        console.log(response1.status) // 200
    }

    return(<>

        <h3> axios 예제 </h3>
        <button onClick={onAxios1}> axios GET </button>
        <button onClick={onAxios2}> axios POST </button>
        <button onClick={onAxios3}> axios PUT </button>
        <button onClick={onAxios4}> axios DELETE </button>
    </>)   
}