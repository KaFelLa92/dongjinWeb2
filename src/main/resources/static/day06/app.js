// app.js
// ** 다른 js export된 자료 가져오기 **
// [1] math.js의 자료 가져오기
import add from "./math.js";
console.log(add( 3, 4));

// [2] config.js의 자료 가져오기
import config from "./config.js";
console.log(config);

// [3] util.js의 자료 가져오기
// default가 아닌 {named export} 자료는 {} 묶는다.
import hello , {PI , E} from "./util.js";
hello( "유재석" );
console.log(PI);
console.log(E)