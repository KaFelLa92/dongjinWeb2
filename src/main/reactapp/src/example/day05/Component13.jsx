// [1] 내가 만든 스토어 불러오기
import store from './store.jsx'
// [2] Store 사용할 곳에 store 공급해주기 , <Provider store={ 내가만든스토어 } >
import { Provider, useDispatch, useSelector } from 'react-redux'
import { login, logout } from './userSlice.jsx';


export default function Component13(props) {
    // [3] store 저장된 상태를 가져오기
    const dispatch = useDispatch();
    //  const { 상태변수명 } = useSelector( (state) => state.슬라이스명 )
    const { isAuthenticated } = useSelector((state) => state.user)
    console.log(isAuthenticated);

    // [4] 로그인 처리
    const loginHandle = () => {
        // 로그인 axios 성공했다는 가정
        dispatch(login()); // dispatch 이용한 login액션 요청한다. dispatch(login리듀서)
    }

    // [5] 로그아웃 처리
    const logoutHandle = () => {
        dispatch(logout()); // dispatch 이용한 logout 액션을 요청한다. dispatch(logout리듀서)
    }

    return (<>
    {/* <Provider store={store}> <Component13 /> </Provider> main.jsx에서 미리 프로바이드 */}
        <h3> 리덕스 예제 </h3>
        {isAuthenticated == true
            ?
            <div>
                <p> 반갑습니다 회원님 </p>
                <button type='button' onClick={logoutHandle}> 로그아웃 버튼 </button>
            </div>
            :
            <div>
                <p> 로그인 상태가 아닙니다. </p>
                <button type='button' onClick={loginHandle}> 로그인 버튼 </button>
            </div>
        }
    </>)
}