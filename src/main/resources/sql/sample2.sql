DROP DATABASE IF EXISTS springweb2;
CREATE DATABASE springweb2;
USE springweb2;

-- --------------------------------------- 실습1 ----------------------------------------
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT, -- 상품 ID (자동 증가)
    product_name VARCHAR(255) NOT NULL,        -- 상품명
    stock_quantity INT NOT NULL                -- 재고 수량
);

INSERT INTO products (product_name, stock_quantity) VALUES
('무선 이어폰', 25),
('스마트워치', 12),
('게이밍 키보드', 30),
('기계식 마우스', 8),
('휴대용 충전기', 15);


-- --------------------------------------- day06 example ----------------------------------------
-- 학생 테이블
CREATE TABLE student (
    sno INT AUTO_INCREMENT,              -- 학생 번호 (자동 증가)
    name VARCHAR(50) NOT NULL,           -- 이름
    kor INT NOT NULL,                    -- 국어 점수
    math INT NOT NULL,                    -- 수학 점수
    CONSTRAINT  PRIMARY KEY (sno)  -- 기본키 제약 조건 추가
);

INSERT INTO student (name, kor, math) VALUES ('홍길동', 85, 90);
INSERT INTO student (name, kor, math) VALUES ('김철수', 78, 88);
INSERT INTO student (name, kor, math) VALUES ('이영희', 92, 95);
INSERT INTO student (name, kor, math) VALUES ('박지민', 70, 65);
INSERT INTO student (name, kor, math) VALUES ('최유리', 88, 82);

select * from student;