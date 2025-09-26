import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./실습8/components/Header";
import HomePage from "./실습8/pages/HomePage";
import MenuPage from "./실습8/pages/MenuPage";
import CartPage from "./실습8/pages/CartPage";
import Footer from "./실습8/components/Header";
import './common.css';

export default function App(props) {

    return (<>
        <BrowserRouter>
            <div>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/menu" element={<MenuPage />} />
                    <Route path="/cart" element={<CartPage />} />
                </Routes>
                <Footer />
            </div>
        </BrowserRouter>
    </>)
}