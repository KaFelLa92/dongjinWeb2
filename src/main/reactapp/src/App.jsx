import { useState } from 'react'
import Component1 from './example/day01/Component1'

const App = () => {
  const [count, setCount] = useState(0)

  return (
    <>
      <div id='container'>
        <h1> 최초 렌더링 되는 컴포넌트(함수)입니다. </h1>
        <Component1 />
      </div>
    </>
  )
}

export default App
