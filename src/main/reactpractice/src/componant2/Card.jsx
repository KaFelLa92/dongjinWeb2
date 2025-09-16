function CardPrint() {
    return (<>
        <div>
            <Card>
                {/* Card 컴포넌트 children으로 전달되는 부분 */}
                <h2> 첫 번째 카드 </h2>
                <p>Card 컴포넌트는 다른 컴포넌트나 엘리먼트를 감쌀 수 있습니다.</p>
            </Card>
        </div>

    </>)
}

const Card = ({ children }) => {
    const cardStyle = {
        padding: '20px',
        margin: '20px',
        boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
        borderRadius: '10px',
        backgroundColor: 'white'
    };

    return (<>
        <div style={cardStyle}>
            {children}
        </div>
    </>)
}

export default Card;