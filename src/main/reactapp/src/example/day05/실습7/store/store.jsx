/*
    스토어 : 여러개의 상태를 보관하는 저장소 , 1개 존재해야한다.
    configureStore()
*/

/*
    퍼시스턴스 : 로컬/세션 스토리지에 상태 유지하는 방법
    1. 설치 : npm i redux-persist
    2. 스토리지설정
*/

// [4] redux-persist 설정 // const persistConfig = { key : 'key이름' , storage }
import storage from 'redux-persist/lib/storage';
const persistConfig = { key : 'user' , storage }

import { configureStore } from "@reduxjs/toolkit";
import userSlice from './userSlice.jsx';

// [5] 리듀서에 persist 설정 적용 , persistedReducer( persist옵션 , 설정할리듀서)
import { persistStore , persistReducer } from 'redux-persist'
const persistedReducer = persistReducer( persistConfig , userSlice )

// [1] 스토어 생성
const store = configureStore( {
    reducer : {
        // [2] 슬라이드 등록
        // user : userSlice // 퍼시스턴스 적용전
        // [6] 퍼시스턴스 적용된 리듀서를 스토어 등록
        user : persistedReducer // 퍼시스턴스 적용후 
    }
})

// [3] export
export default store;
// [7] 등록된 퍼시스턴스 스토어 내보내기
export const persistor = persistStore( store )