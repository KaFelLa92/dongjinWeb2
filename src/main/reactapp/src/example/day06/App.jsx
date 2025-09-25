import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./실습8/components/Header";
import HomePage from "./실습8/pages/HomePage";
import MenuPage from "./실습8/pages/MenuPage";
import CartPage from "./실습8/pages/CartPage";

export default function App(props) {

    return (<>
        <BrowserRouter>
            <div>
                <h3> 루트 페이지 </h3>
                <Header />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/menu" element={<MenuPage />} />
                    <Route path="/cart" element={<CartPage />} />
                </Routes>
            </div>
        </BrowserRouter>
    </>)
}