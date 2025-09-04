console.log('chat XXOK');

// [*] 익명 이름(비회원제)
// Math.floor( Math.random() * 끝값 ) + 시작값
const randomId = Math.floor(Math.random() * 1000) + 1;
const nickName = `익명${randomId}`; // 익명{난수}

// [*] 방 번호 ( url queryString )
const params = new URL(location.href).searchParams
const room = params.get('room') || '0'; // url 경로의 room 번호를 문자 타입으로 가져오고, 없으면 '0'(전체채팅)

// [1] 서버웹소켓과 연동하는 클라이언트소켓 생성
const client = new WebSocket("/chat");

// [2] 서버웹소켓과 연동 성공했을 때
client.onopen = (event) => {
    console.log("서버 소켓과 연동 성공");
    // 1. 방 번호에 특정한 닉네임 *등록* 메시지 보내기
    let msg = { type: "join", room: room, nickName: nickName } // JSON 형식으로 문자열 메시지 보내기
    // JSON.stringfy() : 객체(JSON) 형식을 유지하고 문자열 타입으로 변환
    // JSON.parse() : 문자열 타입을 객체(JSON) 타입으로 변환
    // 2. 클라이언트소켓과 연결된 서버소켓에 메시지 보내기
    client.send(JSON.stringify(msg));
}

// [3] 서버웹소켓과 연동 종료했을 때
client.onclose = (event) => {
    console.log("서버 소켓과 연동 종료")
}

// [4] 서버웹소켓으로부터 메시지 받았을 때
client.onmessage = (event) => {
    console.log("서버소켓으로부터 메시지 받음")
    // 4-1 서버로부터 받은 메시지 확인
    console.log(event); // 해당 메소드의 실행 이유를 알 수 있는 여러 정보의 객체
    console.log(event.data); // 서버로부터 받은 메시지(data) 속성 확인
    // 4-2 받은 메시지를 json 타입으로 변환
    const message = JSON.parse(event.data);
    // 4-3 받은 메시지의 타입을 확인하여 서로 다른 html 만들어주기
    let html = ``;
        // 메시지가 알람이라면
    if (message.type == 'alarm') {
        html += `<div class="alarm">
                    <span> ${message.message} </span>
                </div>`
    }
        // 메시지가 채팅이라면
    else if (message.type == 'msg') {
        // 내가 보낸 메시지
        if (message.from == nickName) {
            html += `<div class="secontent">
                        <div class="date"> ${message.date} </div>
                        <div class="content"> ${message.message} </div>
                    </div>`
        }
        // 남이 보낸 메시지
        else {
            html += `<div class="receiveBox">
                        <div class="profileImg">
                            <img src="default.jpg"/>
                        </div>
                        <div>
                            <div class="recontent">
                                <div class="memberNic"> ${message.from} </div>
                                <div class="subcontent">
                                    <div class="content"> ${message.message} </div>
                                    <div class="date"> ${message.date} </div>
                                </div>
                            </div>
                        </div>
                    </div>`
        }
    }
    // 4-4 구성한 html을 <div class="msgbox">에 추가하기
    document.querySelector(".msgbox").innerHTML += html;

    // 4-5 만약 <div class="msgbox"> 내용물이 고정 사이즈보다 커지면, 자동 스크롤 내리기
    const msgbox = document.querySelector('.msgbox');
        // 현재 msgbox의 스크롤 위치를 가장 하단에 대입하기.
    msgbox.scrollTop = msgbox.scrollHeight
    // DOM객체.scrollTop : 현재 dom(마크업)의 스크롤 상단 꼭지점 위치
    // DOM객체.scrollHeight : 현재 dom(마크업)의 스크롤 전체 길이
}

// [*] 서버웹소켓에서 에러 발생했을 때
client.onerror = (event) => {

}

// [5] 클라이언트소켓이 서버에게 *일반* 메시지 전송하는 버튼 메소드
const onMsgSend = async () => {
    // 5-1. input으로 입력받은 값의 마크업 가져오기
    const msginput = document.querySelector('.msginput');
    const message = msginput.value;
    // 5-2. 만약 입력값이 없으면 종료
    if (message == '') return;
    // 5-3. 메시지 구성하기 
    const msg = {
        type: 'msg', message: message, // type : 메시지 종류 , message : 메시지 내용물
        from: nickName, date: new Date().toLocaleString()
    } // from : 보낸사람 , date : 현재날짜/시간
    // 5-4. 클라이언트소켓에 메시지 보내기
    client.send(JSON.stringify(msg));
    // 5-5. input 마크업 초기화 (채팅 쓰는 창 비우기)
    msginput.value = '';
} // func end

// [6] <input class="msgInput" />에서 enter 입력했을 때
const enterKey = () => {
    // 만약 input에서 enter키를 눌렀을 때
    if (window.event.keyCode == 13) { // keyCode에서 엔터키가 13번임
        // keyCode : k소문자 C대문자
        onMsgSend();    // [5] 메시지 전송 함수 호출
    }
} // func end
