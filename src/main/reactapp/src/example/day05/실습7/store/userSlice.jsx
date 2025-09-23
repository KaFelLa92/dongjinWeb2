import { createSlice } from "@reduxjs/toolkit"

// [1] 전역변수 초기값
const initialState = { isAuthenticated: false, userInfo: null }

// [2] 리듀서 함수 정의
const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        login: (state , action) => { state.isAuthenticated = true; state.userInfo = action.payload },
        logout: (state) => { state.isAuthenticated = false; state.userInfo = null }
    }
})


// [3] store export
export default userSlice.reducer; // 현재 정의 리듀서(reducers)들을 store 등록 예정

// [4] 다른 컴포넌트에서 액션할 수 있도록 export
export const { login, logout } = userSlice.actions;