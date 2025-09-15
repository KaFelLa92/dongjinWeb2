import { useState } from 'react'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
     <h1> 최초 렌더링 되는 컴포넌트(함수)입니다. </h1>
    </>
  )
}

export default App
