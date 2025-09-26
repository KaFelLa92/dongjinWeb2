import { useNavigate } from "react-router-dom"
import Button from '@mui/joy/Button';

// 실습9 : 실습8에 MUI 라이브러리 입히기

export default function HomePage(props) {
    // [*] 키오스크 대기화면에서 메뉴로 가기 위한 navigate
    const navigate = useNavigate();

    const toMenu = () => {
        navigate('/menu');
    }

    return (<>
        <h3> 홈 페이지 </h3>
        <Button onClick={toMenu}> 눌러서 메뉴로 이동 </Button>
    </>)
}