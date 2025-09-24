import { createSlice } from "@reduxjs/toolkit"

// [1] 전역변수 초기값
const initialState = { cartInfo: [] }

// [2] 리듀서 함수 정의
const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {
        add: (state, action) => {
            // 1) 클릭한 메뉴 객체 확인
            const newItem = action.payload
            // 2) 장바구니에 들어있는 상품인지 확인
            const existingItem = state.cartInfo.find((item) => item.id == newItem.id);
            // 3-1) 있는 상품이면 수량 증가
            if (existingItem) {
                existingItem.stock++;
            } else { // 3-2) 없는 상품이면 stock 1을 추가해 cartInfo 배열에 추가
                state.cartInfo.push({ ...newItem, stock: 1 });
            }
        }, // *) 장바구니에서 전체 아이템 제거하는 reducer
        remove: (state) => { state.cartInfo = []; }
    }


})

// [3] store export
export default cartSlice.reducer; // 현재 정의한 리듀서를 스토어에 등록하기 위한 export

// [4] 전역변수 export
export const { add, remove } = cartSlice.actions;