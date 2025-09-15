// util.js
// 1. 함수
const hello = ( name ) => {
    return name + "님";
}
// 2. 함수 내보내기 , export default는 1개만 됨
export default hello;
// 3. 이름을 붙인 내보내기 , named export 여러개 가능 (import에서 중괄호로 묶기)
export const PI = 3.14;
export const E = 2.71;

