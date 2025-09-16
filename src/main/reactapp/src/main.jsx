// import { StrictMode } from 'react'
// import { createRoot } from 'react-dom/client'
// import './index.css'
// import App from './App.jsx'

// createRoot(document.getElementById('root')).render(
//   <StrictMode>
//     <App />
//   </StrictMode>,
// )

// ** main.jsx에서 index.html의 id=root 마크업에 최초의 컴포넌트(화면함수) 렌더링하는 곳 **
// 1. 리액트 라이브러리 createRoot 함수 import하기
import{ createRoot } from 'react-dom/client';
// 2. index.html(SPA)에서 root 마크업 가져오기
const root = document.querySelector('#root');
// 3. 가져온 root 마크업을 createRoot 함수의 매개변수로 전달
const create = createRoot( root );
// 4. root에 렌더링하기
  // 4-1 : 렌더링할 컴포넌트(화면함수) 가져오기
  // import App from './App.jsx';
  // 4-2 : 렌더링하기
  // create.render( <App></App> );

// * 2 ~ 4-2 요약 createRoot( document.querySelector('#root')).render( <최초출력함수명 />);
// createRoot( document.querySelector('#root')).render( <App />);

// day01
// [1] 렌더링 컴포넌트 가져오기
import Component1 from './example/day01/Component1';
import Component2 from './example/day01/Component2';
// [2] 렌더링하기 * 렌더는 한 번만 가능하다. *
// create.render( <div> <Component1 /> </div> );
create.render( <div> <Component2 /> </div> );

