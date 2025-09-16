import './Header.css';

const Header = (props) => {
    return (<>
        <img src="/images/pepperSolo.png" alt="logo" width="150px" />
        <h2> <span>S</span>tay <span>KO</span>rean <span>VILL</span>age </h2>
        <nav>
            <ul>
                <li> Home </li>
                <li> About </li>
                <li> Contant </li>
            </ul>
        </nav>
    </>)
}

export default Header;