export default function ImageCardDisplay() {
    const imageData = [
        {
            img: "https://m.health.chosun.com/site/data/img_dir/2025/04/08/2025040803041_0.jpg",
            cap: "갱얼쥐 사진입니더"
        },
        {
            img: "http://file.daesoon.org/webzine/307/202212191656_Daesoon_263_%EB%AC%B8%ED%99%94%EC%82%B0%EC%B1%85_%EC%A0%84%EA%B2%BD%20%EC%86%8D%20%EB%8F%99%EB%AC%BC%20%EA%B3%A0%EC%96%91.jpg",
            cap: "괭이 사진입니더"
        }
    ];

    return (<>
        <div style={{ display: 'flex' }}>
            <ImageCardForm imageUrl={imageData[0].img} caption={imageData[0].cap} />
            <ImageCardForm imageUrl={imageData[1].img} caption={imageData[1].cap} />
        </div>
    </>);
}


// props를 객체 구조 분해 할당으로 받습니다. { imageUrl, caption }
function ImageCardForm({ imageUrl, caption }) {

    const cardStyle = {
        border: '1px solid #ccc',
        borderRadius: '8px',
        padding: '10px',
        margin: '10px',
        width: '300px',
        textAlign: 'center'
    };

    const imageStyle = {
        width: '100%',
        height: '200px',
        objectFit: 'cover'
    };

    return (
        <div style={cardStyle}>
            <img src={imageUrl} alt={caption} style={imageStyle} />
            <p>{caption}</p>
        </div>
    );
}






/* 
  이렇게 수정한 ImageCard 컴포넌트는 다른 파일에서 아래와 같이 재사용할 수 있습니다.

  // App.jsx 예시
  import ImageCard from './componant2/ImageCard';

  function App() {
    const imageData = [
        { 
            img: "https://m.health.chosun.com/site/data/img_dir/2025/04/08/2025040803041_0.jpg", 
            cap: "갱얼쥐 사진입니더" 
        },
        { 
            img: "http://file.daesoon.org/webzine/307/202212191656_Daesoon_263_%EB%AC%B8%ED%99%94%EC%82%B0%EC%B1%85_%EC%A0%84%EA%B2%BD%20%EC%86%8D%20%EB%8F%99%EB%AC%BC%20%EA%B3%A0%EC%96%91.jpg", 
            cap: "괭이 사진입니더" 
        }
    ];

    return (
      <div style={{ display: 'flex' }}>
        <ImageCard imageUrl={imageData[0].img} caption={imageData[0].cap} />
        <ImageCard imageUrl={imageData[1].img} caption={imageData[1].cap} />
      </div>
    );
  }
*/