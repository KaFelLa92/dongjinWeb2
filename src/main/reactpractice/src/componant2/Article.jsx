import Footer from "../componant1/Footer"
import Header from "../componant1/Header"

export default function ArticleForm() {

    return (<>
        <Header />
        <h1> 우리신문 영화평론 기사 페이지 </h1>
        <Article></Article>
        <Footer />
    </>)
}

const Article = ({ title, author, content }) => {

    const obj = [{
        title: "명징하게 직조해낸 계급의 적강과 상승",
        author: "이동진",
        content: "기생충 리뷰입니다."
    }, {
        title: "나가라",
        author: "박평식",
        content: "나가요 미스콜 리뷰입니다."
    }]

    return (<>

        <h2> {obj[0].title} </h2>
        <h4> 작성자 : {obj[0].author} </h4>
        <p> {obj[0].content} </p>

        <h2> {obj[1].title} </h2>
        <h4> 작성자 : {obj[1].author} </h4>
        <p> {obj[1].content} </p>
    </>)

}