export default function UserPrint() {
    const user1 = {
        name: '나루토',
        age: 36,
        hobbies: ['라멘', '인술', '구미차크라']
    }
    const user2 = {
        name: '엘런예거',
        age: 22,
        hobbies: ['거인화', '구축', '의지드립']
    }

    return (
        <>
            <h1> 사용자 프로필 </h1>
            <UserProfile
                name={user1.name}
                age={user1.age}
                hobbies={user1.hobbies}
            />
            <UserProfile
                name="잼민이"
                age={1}
                hobbies={["가짜뉴스 살포", "DB 만들기"]}
            />
            <UserProfile
                name={user2.name}
                age={user2.age}
                hobbies={user2.hobbies}
            />
        </>
    )
}


function UserProfile({ name, age, hobbies }) {
    return (<>
        <div style={{ border: '1px solid #ccc', margin: '10px', padding: "10px" }}>
            <h2> {name} </h2>
            <p> 나이 : {age} 세 </p>
            <h4> 취미 : </h4>
            <ul>
                {hobbies.map(hobby => <li key={hobby}> {hobby} </li>)}
            </ul>
        </div>
    </>)
}

