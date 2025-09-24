import { configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import cartSlice from './cartSlice.jsx';

// [2] redux-persist 설정
import storage from 'redux-persist/lib/storage';
const persistConfig = { key: 'cart', storage }

// [3] 리듀서에 persist 설정 적용
const persistedReducer = persistReducer(persistConfig, cartSlice)

// [1] 스토어 생성
const store = configureStore({
    reducer: {
        // [4] 퍼시스턴스 적용된 리듀서 스토어 등록
        cart: persistedReducer
    }
})

// [5] export
export default store;
// [6] 등록된 퍼시스턴스 스토어 내보내기
export const persistor = persistStore(store)