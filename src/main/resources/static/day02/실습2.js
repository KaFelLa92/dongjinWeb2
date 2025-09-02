console.log('실습2 XXOK');

// [1] 클라이언트 접속 요청
// const client = new Websocket("ws://localhost:8080/alarm");
const client = new WebSocket('/alarm');

/*
    2. 클라이언트가 웹소켓 서버에 접속하면, 현재 접속한 모든 클라이언트에게
    alert("익명의 유저가 접속했습니다.") 알림이 표시되도록 한다.
*/

client.onopen = (event) => {
    console.log("[클라이언트] 서버소켓과 연동 성공")
    alert('익명의 유저가 접속했습니다.');
}

/*
    3.  클라이언트가 연결을 종료했을 때는, 현재 접속한 모든 클라이언트에게
    alert("익명의 유저가 퇴장했습니다.") 알림이 표시되도록 한다.

*/

client.onclose = (event) => {
    console.log("[클라이언트] 서버소켓과 연동 종료");
    alert('익명의 유저가 퇴장했습니다.');

}

// [2] 메시지 전송은 .onmessage에서 처리
client.onmessage = (event) => {
    console.log(event);         // 서버에서 받은 이벤트 정보 객체
    console.log(event.data);    // 이벤트 정보 객체 내 data 속성 메시지
    const alertBox = document.querySelector('.alertBox');
    let html = `<div> ${event.data} </div>`;
    alertBox.innerHTML += html;

}