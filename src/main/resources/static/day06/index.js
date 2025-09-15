console.log('index XXOK');

// [1] 변수와 상수 키워드

let count = 10;     // 변수의 선언과 초기화
console.log(count);
count = 20;         // 변수값 수정

const count2 = 20;  // 변수의 선언
console.log(count2);
// count2 = 30; 수정 안 됨 상수라서

console.log(count + count2);

const obj = {
    name: "유재석",
    age: 40
}                       // 변수, 상수에는 객체 담기 가능

const method = () => {  // 변수, 상수에는 함수 담기 가능

}

const arr = ["유재석", "강호동"]

// JS는 타입 확인이 어려워서, 타입스크립트를 쓰기도 함

// * var 키워드 : let 이전의 키워드
var count3 = 30;
var count3 = 50; // var 키워드는 중복 변수명 허용하여 식별이 어려움
console.log(count3)

// [2] 백틱 : 문자열 템플릿 , 문자열내 JS표현식을 연결할 때 사용. 리액트는 JSX 사용
console.log(`Hello : ${count}`);
let html = ``;
html += `<div> Hello : ${count} </div>`
console.log(html);

// [3-1] 조건문1 : IF
const point = 85;
if (point >= 90) {
    console.log("A학점")
} else if (point >= 80) {
    console.log("B학점")
} else {
    console.log("C학점")
}

// [3-2] 조건문2 : 삼항연산자 , 조건 ? 참 : 거짓 (간단한 조건에서 주로 사용됨 react에서 많이 씀)
console.log(point >= 90 ? "A학점" : point >= 80 ? "B학점" : "C학점");

// [3-3] 조건문3 : 단축평가 , 조건 && 참이면 결과 , 조건 || 거짓이면 결과
console.log(point >= 90 && "A학점");  // false
console.log(point >= 90 || "A학점");  // A학점
console.log(point >= 80 && "A학점");  // A학점
console.log(point >= 80 || "A학점");  // true

// [4] 반복문1 :
// 1) for문
const array = [10, 20, 30, 40, 50]
for (let i = 0; i < array.length; i++) {
    console.log(array[i])
}
// 2) 향상된 for문
for (let i in array) {
    console.log(array[i])
}
// 3) 향상된 for문 2
for (let v of array) {
    console.log(v);
}
// 4) forEach
array.forEach((v) => {
    console.log(v);
});
// 5) **map** : forEach와 다르게 return 가능
const newArray = array.map((v) => {
    console.log(v);
    return v;
})
console.log(newArray); // [10, 20, 30, 40, 50]
// 6) **filter** : 조건부 return 가능하다.
const filterArray = array.filter((value) => {
    console.log(value)
    return value > 20;
})
console.log(filterArray); // [30, 40, 50]

// [5] 함수 :
// 1) 선언적 함수
function fun1(param1, param2) { }
// 2) 익명 함수
const fun2 = function (param1, param2) { }
// 3) 람다식(화살표) 함수
const fun3 = (param1, param2) => { }
// 4) 매개변수 기본값
const fun4 = (param1 , param2 = "강호동") => { } 

fun1( 4, 10 ) // 함수 호출
fun3( 10, "유재석") // 함수 호출
fun3( 10, { name : "유재석" } ); // 함수 호출 
fun4( 10 )

// [6] 객체 : 여러개의 값을 가진 값
// 변수와 상수는 값을 저장하는 상징적인 이름.
// 값은 리터럴vs객체/배열 객체는 value와 엮임

const obj1 = { name : "유재석" , age : 40 };
const name2 = "강호동";
const age2 = 50;
const obj2 = { name2, age2 }; // key와 value 변수명이 같으면 key 생략 가능
const obj3 = [ "유재석" , 40 ];
console.log(obj1.name)  // 유재석
console.log(obj3[0]);   // 유재석
// 스프레드 연산자 ... 배열이나 객체를 복사할 때 사용 , 왜? 주소값 변경 목적
const obj4 = { ...obj1 , phone : "010" }
console.log(obj4)       // {name: '유재석', age: 40, phone: '010'}
const obj5 = [ ...obj3 ];
console.log(obj5)       // ['유재석', 40] 값 차이는 없지만 새로운 주소값으로 복사됨
const obj6 = [ 6, 7 , ...obj3 ]; console.log(obj6);

// [7] ** 구조 분해 할당 : 객체나 배열에서 값을 분해하는 방법



