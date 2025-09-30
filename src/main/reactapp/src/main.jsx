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
import { createRoot } from 'react-dom/client';
// 2. index.html(SPA)에서 root 마크업 가져오기
const root = document.querySelector('#root');
// 3. 가져온 root 마크업을 createRoot 함수의 매개변수로 전달
const create = createRoot(root);
// 4. root에 렌더링하기
// 4-1 : 렌더링할 컴포넌트(화면함수) 가져오기
// import App from './App.jsx';
// 4-2 : 렌더링하기
// create.render( <App></App> );

// * 2 ~ 4-2 요약 createRoot( document.querySelector('#root')).render( <최초출력함수명 />);
// createRoot( document.querySelector('#root')).render( <App />);

// [1] 렌더링 컴포넌트 가져오기
// day01
import Component1 from './example/day01/Component1';
import Component2 from './example/day01/Component2';
import Component3 from './example/day01/Component3';
import Task1 from './example/day01/Task1';
import Task2 from './example/day01/Task2';

// day02
import Component4 from './example/day02/Component4';
import Component5 from './example/day02/Component5';
import Component6 from './example/day02/Component6';
import Component7 from './example/day02/Component7';
import Task3 from './example/day02/Task3';
import Task4 from './example/day02/Task4';

// day03
import Component8 from './example/day03/Component8';
import Component9 from './example/day03/Component9';
import Component10 from './example/day03/Component10';
import Task5 from './example/day03/Task5';
import Task51 from './example/day03/Task51';

// eval6
import Eval6 from './eval6/eval6';

// day04
import Component11 from './example/day04/Component11';
import Component12 from './example/day04/Component12';
import Task6 from './example/day04/Task6';

// day05
import Component13 from './example/day05/Component13';
// [1] 내가 만든 스토어 불러오기
// import store, { persistor } from './example/day05/실습7/store/store';
// [2] Store 사용할 곳에 store 공급해주기 , <Provider store={ 내가만든스토어 } >
// import { Provider } from 'react-redux';

// [2] 렌더링하기 * 렌더는 한 번만 가능하다. *
// create.render( <div> <Component1 /> </div> );
// create.render( <div> <Component2 /> </div> );

// 실습7
// import App from './example/day05/실습7/App';

// 실습8
// import App from './example/day06/App';
// import { PersistGate } from 'redux-persist/integration/react';
// import store, { persistor } from './example/day06/실습8/store/store';
// import { Provider } from 'react-redux';

// create.render(
//   <Provider store={store}>
//     {/* [2] 내가 만든 persist 공급 , loading : { 초기 로딩값 } psersist = { 내가만든persistStore } */}
//     <PersistGate loading={null} persistor={persistor}>
//       <App />
//     </PersistGate>
//   </Provider>
// );

// day07
// import Component14 from './example/day07/Component14';
// import SideBar from './example/day07/SideBar';
// import ExampleGmailList from './example/day07/Navigation';
// import BasicModal from './example/day07/Modal';

// create.render(
//   <BasicModal />
// );

// day08
import Component15 from './example/day08/Component15';
create.render(<Component15 />);
