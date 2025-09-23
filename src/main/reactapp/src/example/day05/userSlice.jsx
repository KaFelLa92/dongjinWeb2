// [1] 리덕스 설치 : 
// npm i @reduxjs/toolkit
// npm i react-redux

import { createSlice } from "@reduxjs/toolkit";

// [2] 전역변수의 초기값 : '로그인 여부'를 저장하는 상태 true(로그인) false(로그인안됨)
const initialState = { isAuthenticated : false }

// [3] 상태를 변경하는 리듀서 함수들을 정의
// createSlice( { name : 'slice이름' , 초기값 , reducers : { 액션함수명 : (state) => { } } });
const userSlice = createSlice ( { 
    name : "user" , // slice의 이름 , 저장소(store)에 저장되는 일부분의 *값*의 이름
    initialState , // [2]에서 정의한 객체로 초기값을 설정 , 추후에 다양하게 저장 가능 , 단 노출이 위험한 정보 제외
    reducers : { 
        login : ( state ) => { state.isAuthenticated = true; } , // 로그인 액션(로그인함수가 실행되면 처리되는 코드)
        logout : ( state ) => { state.isAuthenticated = false; } // 로그아웃 액션(로그아웃 함수가 실행되면 처리되는 코드)
    } , // slice(상태)가 변경되는 방법(함수) 정의
});

// [4] 내보내기
// [4-1] export default 내보내기는 파일내 1개만 가능 , store에 리듀서를 import할 수 있게
// 모든 컴포넌트에서 로그인과 로그아웃 사용 가능
export default userSlice.reducer 
// [4-2] export 내보내기는 여러번 가능
// login액션, logout액션을 다른 컴포넌트에서 import할 수 있게
export const { login , logout } = userSlice.actions; 



