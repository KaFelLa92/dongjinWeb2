# React 핵심 요약 정리

이 문서는 제공된 텍스트 파일과 예제 코드를 바탕으로 React의 핵심 개념을 요약 정리한 자료입니다.

## 1. 컴포넌트 (Component)

### 1.1. 정의 및 특징
- **정의**: React에서 독립적이고 재사용 가능한 UI 단위입니다.
- **특징**: HTML, JavaScript, CSS를 하나의 파일에서 관리할 수 있습니다.

### 1.2. 컴포넌트 생성 규칙
1.  **함수 선언**: `function` 키워드 또는 화살표 함수로 선언합니다.
2.  **컴포넌트명**: 첫 글자는 반드시 **대문자**로 시작해야 합니다. (예: `MyComponent`)
3.  **Props**: 부모 컴포넌트로부터 전달받는 속성을 매개변수(`props`)로 받을 수 있습니다.
4.  **return**: JSX 문법을 사용하여 UI를 반환합니다. 2줄 이상의 JSX는 `()`로 감싸는 것이 관례입니다.
5.  **export**: `export default`를 사용하여 컴포넌트를 다른 파일에서 사용할 수 있도록 내보냅니다.

```jsx
// src/components/MyComponent.jsx
export default function MyComponent(props) {
  // JavaScript 코드 작성 영역
  
  return (
    <div>안녕하세요, {props.name}님!</div>
  );
}
```

### 1.3. 컴포넌트 호출
- **다른 파일**: `import` 키워드로 컴포넌트를 불러온 후 `<컴포넌트명 />` 형태로 사용합니다.
- **같은 파일**: 별도의 `import` 없이 `<컴포넌트명 />` 형태로 사용합니다.

```jsx
import MyComponent from './components/MyComponent';

function App() {
  return <MyComponent name="React" />;
}
```

## 2. JSX (JavaScript XML)

- **정의**: JavaScript 내에서 HTML과 유사한 마크업을 작성할 수 있게 해주는 확장 문법입니다. 브라우저는 JSX를 직접 해석할 수 없으므로 Babel과 같은 트랜스파일러를 통해 JavaScript로 변환됩니다.

### 2.1. 주요 문법 규칙
1.  **하나의 부모 요소**: 모든 요소는 반드시 하나의 부모 요소로 감싸져야 합니다. 불필요한 `div`를 피하고 싶을 경우 `Fragment(<></>)`를 사용합니다.
2.  **태그 닫기**: 모든 태그는 반드시 닫혀야 합니다. (예: `<input />`, `<br />`)
3.  **JavaScript 표현식**: JSX 내에서 JavaScript 코드를 사용하려면 `{}` 중괄호를 사용합니다.
4.  **주석**: `{/* 주석 내용 */}` 형태로 작성합니다.
5.  **이벤트 핸들러**: `onClick`, `onChange` 등 이벤트 속성은 카멜 케이스(camelCase)로 작성하며, 함수 자체를 `{}` 안에 전달합니다. (예: `onClick={handleClick}`)

## 3. Props

- **정의**: 부모 컴포넌트가 자식 컴포넌트에게 데이터를 전달하기 위한 속성(properties)입니다.
- **특징**:
    - **읽기 전용 (Read-only)**: 자식 컴포넌트는 전달받은 `props`를 직접 수정할 수 없습니다.
    - **단방향 데이터 흐름**: 데이터는 부모에서 자식으로만 흐릅니다.

```jsx
// 부모 컴포넌트
function Parent() {
  return <Child name="유재석" age={40} />;
}

// 자식 컴포넌트 (구조 분해 할당 사용)
function Child({ name, age }) {
  return <p>{name} ({age}세)</p>;
}
```

## 4. 생명주기(Lifecycle)와 훅(Hooks)

### 4.1. 생명주기
컴포넌트가 생성되고, 업데이트되고, 소멸하기까지의 과정을 의미합니다.
- **Mount (생성)**: 컴포넌트가 처음으로 렌더링될 때.
- **Update (업데이트)**: `props`나 `state`가 변경되어 리렌더링될 때.
- **Unmount (소멸)**: 컴포넌트가 화면에서 사라질 때.

### 4.2. 훅 (Hooks)
- **정의**: 함수형 컴포넌트에서 `state` 관리, 생명주기 제어 등 클래스형 컴포넌트의 기능을 사용할 수 있게 해주는 함수들입니다. `use`로 시작하는 특징이 있습니다.

## 5. 주요 훅 (Hooks) 소개

### 5.1. `useEffect`
- 컴포넌트의 생명주기 시점에 따라 특정 코드를 실행할 수 있게 합니다.
- **`useEffect(콜백함수, [의존성 배열])`**
    - **의존성 배열 `[]` (Mount)**: 컴포넌트가 처음 렌더링될 때 **한 번만** 실행됩니다.
    - **의존성 배열 `[state, props]` (Update)**: 배열 안의 `state`나 `props` 값이 변경될 때마다 실행됩니다.
    - **의존성 배열 생략 (Mount & Update)**: 렌더링될 때마다 실행됩니다.

### 5.2. `useRef`
- 렌더링과 상관없이 값을 유지하거나, DOM 요소에 직접 접근할 때 사용합니다.
- `useRef`로 생성된 객체의 `.current` 프로퍼티를 통해 값에 접근하거나 변경할 수 있습니다.
- `.current` 값이 변경되어도 컴포넌트는 **리렌더링되지 않습니다.**

```jsx
function TextInput() {
  const inputRef = useRef(null);

  const handleFocus = () => {
    inputRef.current.focus(); // input 요소에 포커스
    console.log(inputRef.current.value); // input 값 접근
  };

  return (
    <>
      <input ref={inputRef} type="text" />
      <button onClick={handleFocus}>포커스 주기</button>
    </>
  );
}
```

## 6. 라우팅 (Routing) with `react-router-dom`

- **정의**: 사용자가 요청한 URL에 따라 해당하는 컴포넌트를 보여주는 기술입니다.
- **설치**: `npm install react-router-dom`

### 6.1. 주요 컴포넌트 및 훅
- `<BrowserRouter>`: 라우팅을 적용할 최상위 컴포넌트.
- `<Routes>`: 여러 `<Route>`를 감싸는 컨테이너. URL과 매칭되는 첫 번째 `<Route>`를 렌더링합니다.
- `<Route path="경로" element={<컴포넌트 />} />`: 특정 경로와 컴포넌트를 매핑합니다.
- `<Link to="경로">링크 텍스트</Link>`: 페이지를 새로고침하지 않고 지정된 경로로 이동하는 링크를 생성합니다. (`<a>` 태그 대신 사용)

### 6.2. URL 파라미터 처리
- **Path Variable (`/product/:id`)**:
    - `<Route path="/product/:name/:price" ... />` 형태로 경로를 정의합니다.
    - `useParams()` 훅을 사용하여 `{ name, price }`와 같이 객체 형태로 파라미터 값을 받습니다.
- **Query String (`/mypage?name=유재석`)**:
    - `useSearchParams()` 훅을 사용합니다.
    - `const [searchParams] = useSearchParams();`
    - `const name = searchParams.get('name');` 형태로 값을 조회합니다.

### 6.3. 페이지 이동
- `useNavigate()`: 특정 이벤트가 발생했을 때 프로그래밍적으로 페이지를 이동시킬 때 사용합니다.
    - `const navigate = useNavigate();`
    - `navigate('/home');` 또는 `navigate(-1);` (뒤로 가기) 형태로 사용합니다.
