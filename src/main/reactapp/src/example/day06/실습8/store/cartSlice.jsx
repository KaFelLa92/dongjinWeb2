import { createSlice } from "@reduxjs/toolkit"

// [1] 전역변수 초기값
const initialState = { cartInfo : [] }

// [2] 리듀서 함수 정의
const cartSlice = createSlice({
    name: 'cart' ,
    initialState,
    reducers: {
        add: (state , action) => {state.cartInfo = action.payload}
    }
})

// [3] store export
export default cartSlice.reducer; // 현재 정의한 리듀서를 스토어에 등록하기 위한 export

// [4] 전역변수 export
export const add = cartSlice.actions.add;