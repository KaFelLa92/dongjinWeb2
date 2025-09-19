const products = [
    { title: "무선 키보드", price: 45000, inStock: true },
    { title: "게이밍 마우스", price: 32000, inStock: false },
    { title: "27인치 모니터", price: 280000, inStock: true }
];

// [3] CSS 파일 불러오기 : import 'CSS파일경로'

// [1] 대표(default) 컴포넌트 만들기
export default function Task2(props) {

    return (<>
        <div class="products">
            {/* 하위 컴포넌트 호출과 동시에 props 속성 자료 전달 */}
            <Product p={products[0]} />
            <Product p={products[1]} />
            <Product p={products[2]} />
        </div>
    </>)

} // func

// [2] 하위 컴포넌트 : 제품 1개당 정보 구성하는 컴포넌트
function Product(props) {
    // 구문 분해 : props 현재 상태 { p : { title , price , inStock }}
    const{ title , price , inStock} = props.p
    console.log(title)

    return (<>
        <ul class="info">
            <li> <h2> {title} </h2> </li>
            <li> 가격 : {price.toLocaleString()} </li>
            <li> {inStock == true ? <div class="checkTrue"> 재고 있음 </div> : <div class="checkFalse"> 재고 없음 </div> } </li>
        </ul>

    </>)

} // func end

// [*] 쓸 있?
const StockCheck = (props) => {
    const ifcheck = props.p.inStock;
    if (ifcheck == true) {
        return (<>
            <p> 재고 있음 </p>
        </>)
    } else {
        return (<>
            <p> 재고 없음 </p>
        </>)
    }
}