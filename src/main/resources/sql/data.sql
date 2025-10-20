
INSERT INTO products (product_name, stock_quantity) VALUES
('무선 이어폰', 25),
('스마트워치', 12),
('게이밍 키보드', 30),
('기계식 마우스', 8),
('휴대용 충전기', 15);


-- --------------------------------------- day06 example ----------------------------------------

INSERT INTO student (name, kor, math) VALUES ('홍길동', 85, 90);
INSERT INTO student (name, kor, math) VALUES ('김철수', 78, 88);
INSERT INTO student (name, kor, math) VALUES ('이영희', 92, 95);
INSERT INTO student (name, kor, math) VALUES ('박지민', 70, 65);
INSERT INTO student (name, kor, math) VALUES ('최유리', 88, 82);

-- --------------------------------------- day07 boardService13 ----------------------------------------

INSERT INTO board (bcontent, bwriter) VALUES ('안녕하세요', '유재석');
INSERT INTO board (bcontent, bwriter) VALUES ('오늘도 좋은 하루 되세요!', '김태호');
INSERT INTO board (bcontent, bwriter) VALUES ('점심 뭐 드셨나요?', '박명수');
INSERT INTO board (bcontent, bwriter) VALUES ('날씨가 참 좋네요', '정준하');
INSERT INTO board (bcontent, bwriter) VALUES ('이번 주말 계획은?', '하하');
INSERT INTO board (bcontent, bwriter) VALUES ('파이팅입니다!', '이광수');
INSERT INTO board (bcontent, bwriter) VALUES ('다들 행복하세요', '송지효');
INSERT INTO board (bcontent, bwriter) VALUES ('무한도전 그립다', '노홍철');
INSERT INTO board (bcontent, bwriter) VALUES ('커피 한 잔의 여유', '길');
INSERT INTO board (bcontent, bwriter) VALUES ('오늘은 집에서 푹 쉬어요', '정형돈');
INSERT INTO board (bcontent, bwriter) VALUES ('운동은 하셨나요?', '김종국');
INSERT INTO board (bcontent, bwriter) VALUES ('영화 추천해 주세요', '양세찬');
INSERT INTO board (bcontent, bwriter) VALUES ('책 읽기 좋은 날이네요', '전소민');
INSERT INTO board (bcontent, bwriter) VALUES ('비가 와서 기분이 우울해요', '유희열');
INSERT INTO board (bcontent, bwriter) VALUES ('점심 메뉴 고민중...', '이효리');
INSERT INTO board (bcontent, bwriter) VALUES ('졸려요...', '강호동');
INSERT INTO board (bcontent, bwriter) VALUES ('주말까지 며칠 남았죠?', '이수근');
INSERT INTO board (bcontent, bwriter) VALUES ('야근은 너무 싫어요', '서장훈');
INSERT INTO board (bcontent, bwriter) VALUES ('오늘 기온이 몇 도일까요?', '장도연');
INSERT INTO board (bcontent, bwriter) VALUES ('여름엔 역시 냉면', '홍진경');
INSERT INTO board (bcontent, bwriter) VALUES ('지금 듣는 노래는?', '장성규');
INSERT INTO board (bcontent, bwriter) VALUES ('퇴근하고 싶다', '이상민');
INSERT INTO board (bcontent, bwriter) VALUES ('다이어트는 내일부터', '박나래');
INSERT INTO board (bcontent, bwriter) VALUES ('버스 기다리는 중', '김숙');
INSERT INTO board (bcontent, bwriter) VALUES ('늦잠 잤어요', '이영자');
INSERT INTO board (bcontent, bwriter) VALUES ('스트레스 풀고 싶다', '유세윤');
INSERT INTO board (bcontent, bwriter) VALUES ('아이스 아메리카노 최고', '김신영');
INSERT INTO board (bcontent, bwriter) VALUES ('게임 하다 왔어요', '이홍기');
INSERT INTO board (bcontent, bwriter) VALUES ('일상 공유해요~', '김재중');
INSERT INTO board (bcontent, bwriter) VALUES ('오늘 하루 어땠나요?', '장나라');
INSERT INTO board (bcontent, bwriter) VALUES ('마라탕 먹고 싶다', '현아');
INSERT INTO board (bcontent, bwriter) VALUES ('퇴근길 너무 막혀요', '정은지');
INSERT INTO board (bcontent, bwriter) VALUES ('곧 여행 가요!', '수지');
INSERT INTO board (bcontent, bwriter) VALUES ('카페에서 힐링 중', '아이유');
INSERT INTO board (bcontent, bwriter) VALUES ('노래 추천해 주세요~', '로제');
INSERT INTO board (bcontent, bwriter) VALUES ('배가 너무 고파요', '지수');
INSERT INTO board (bcontent, bwriter) VALUES ('헬스장 다녀왔어요', '제니');
INSERT INTO board (bcontent, bwriter) VALUES ('오늘도 화이팅!', '리사');
INSERT INTO board (bcontent, bwriter) VALUES ('운전 조심하세요~', '태연');

-- --------------------------------------- day09 trans ----------------------------------------

INSERT INTO trans (name, money) VALUES
('신동엽', 200000),
('서장훈', 200000);

-- --------------------------------------- 실습3  ----------------------------------------

-- 3. 샘플 데이터 (책 목록)
INSERT INTO books (id, title, stock) VALUES (1, '자바의 정석', 3);
INSERT INTO books (id, title, stock) VALUES (2, '스프링 인 액션', 2);
INSERT INTO books (id, title, stock) VALUES (3, '토비의 스프링', 1);
INSERT INTO books (id, title, stock) VALUES (4, '리액트 교과서', 5);

-- 4. 샘플 데이터 (대출 기록)
INSERT INTO rentals (id, book_id, member) VALUES (1, 1, '홍길동');

-- 인덱스 예제

-- 샘플 데이터

INSERT INTO employee (name, dept_id, salary, hire_date, email) VALUES
('유재석', 1, 5000, '2023-01-10', 'yu@test.com'),
('강호동', 2, 4000, '2024-02-12', 'kang@test.com'),
('신동엽', 1, 7000, '2022-06-05', 'shin@test.com'),
('이수근', 2, 5500, '2025-03-22', 'lee@test.com'),
('하하', 3, 3500, '2025-04-10', 'haha@test.com'),
('정형돈', 1, 6200, '2023-07-11', 'don@test.com'),
('박명수', 2, 4800, '2023-09-02', 'park@test.com'),
('노홍철', 3, 3700, '2024-05-14', 'noh@test.com'),
('김종국', 1, 8000, '2022-11-01', 'kim@test.com'),
('양세형', 2, 4300, '2024-06-21', 'yang@test.com'),
('이광수', 3, 3900, '2023-12-12', 'kwang@test.com'),
('조세호', 1, 5100, '2023-03-18', 'cho@test.com'),
('김용만', 2, 4600, '2022-09-23', 'yong@test.com'),
('정준하', 3, 3600, '2024-04-04', 'jun@test.com'),
('김태호', 1, 9000, '2021-10-15', 'taeho@test.com'),
('서장훈', 2, 5800, '2024-08-25', 'seo@test.com'),
('전현무', 3, 4000, '2022-12-01', 'jeon@test.com'),
('김구라', 1, 7500, '2023-11-05', 'gura@test.com'),
('유병재', 2, 4200, '2025-01-20', 'yoo@test.com'),
('김민아', 3, 3800, '2024-10-08', 'mina@test.com');