import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";

export default function Dashboard(props) {

    // [1] store 가져오기
    const dispatch = useDispatch();
    const { isAuthenticated } = useSelector ((state) => state.user)
    
    // [2] useNavigate 가져오기
    const navigate = useNavigate();

    if( isAuthenticated == false){
        alert('정상적인 경로가 아닙니다. 로그인해주세요.')
        navigate("/")
        return null;
    }

    return (<>
        <h3> 대시보드 </h3>
        <p> 대시보드 페이지입니다 </p>
    </>)
}