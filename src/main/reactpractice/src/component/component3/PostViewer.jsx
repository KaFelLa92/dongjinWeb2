import { useEffect, useState } from "react";

export default function PostViewer() {

    const [post, setPost] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // useEffect 내부에서 async 함수를 선언하고 바로 호출합니다.
        const fetchData = async () => {
            try {
                const response = await fetch('https://jsonplaceholder.typicode.com/posts/1');
                const data = await response.json();
                setPost(data);
                setLoading(false);
                console.log(data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchData(); // 선언한 async 함수를 호출

    }, []); // 빈배열

    return (<>
        <div>
            <h1> 게시판 뷰어 </h1>
            {loading ? (
                <p>로딩 중...</p>
            ) : (
                <div>
                    <h2>{post.title}</h2>
                    <p>{post.body}</p>
                </div>
            )}
        </div>
    </>)

}