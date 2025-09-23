/*
    스토어 : 여러개의 상태를 보관하는 저장소 , 1개 존재해야한다.
    configureStore()
*/

import { configureStore } from "@reduxjs/toolkit";
import userReducer from './userSlice.jsx';

// [1] 스토어 생성
const store = configureStore( {
    reducer : {
        // [2] 슬라이드 등록
        user : userReducer
    }
})

// [3] export
export default store;