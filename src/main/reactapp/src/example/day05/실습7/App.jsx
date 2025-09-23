import { BrowserRouter, Route, Routes } from "react-router-dom"
import Header from "./components/Header"
import HomePage from "./pages/HomePage"
import LoginPage from "./pages/LoginPage"
import ProfilePage from "./pages/ProfilePage"
import Dashboard from "./pages/Dashboard"
import Page404 from "./pages/Page404"

export default function App(props) {


    return (<>
        <BrowserRouter>
            <h3> 루트 페이지 </h3>
            <div>
                <Header />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/profile" element={<ProfilePage />} />
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="*" element={<Page404 />} />
                </Routes>
            </div>
        </BrowserRouter>
    </>)
}