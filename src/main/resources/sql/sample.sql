DROP DATABASE IF EXISTS springweb2;
CREATE DATABASE springweb2;

/*
	SQL 
		DDL : 정의어
			CREATE		테이블/DB 생성
            DROP		테이블/DB 삭제
            * ALTER		테이블 정보 수정
		DML : 조작어
			SELECT
            UPDATE
            DELETE
            INSERT
		DCL : 제어어
*/

USE springweb2;

# [1] 생생된 테이블 수정하기
DROP TABLE IF EXISTS employee;
create TABLE employee(
	id int ,
    name varchar(50),
    dept varchar(30),
    constraint employee_id primary key(id)
);
# [2] 기존 테이블의 필드 추가
# ALTER TABLE 테이블명 ADD column 새로운필드명 타입및제약조건;
ALTER TABLE employee add column age int default 10;

ALTER table employee add column date Date;

# [3] 기존 테이블의 필드 타입 수정
# alter table 테이블명 modify column 수정할필드명 새로운타입/제약조건
alter table employee modify dept longtext;

# [4] 기존 테이블의 필드명 수정
# alter table 테이블명 change column 기존필드명 새로운필드명 타입;
alter table employee change column name nickname varchar(100);

# [5] 기존 테이블의 필드명 삭제
# alter table 테이블명 drop column 삭제할필드명
alter table employee drop column date;

# [6] 특정한 테이블의 필드 확인
show columns from employee; -- 특정한 테이블의 필드 정보 확인(속성 정보 확인)

# [7] 제약조건 추가
# alter table 테이블명 add constraint 제약조건(아무거나/중복불가) primary key (PK필드명);
# alter table 테이블명 add constraint 제약조건명 foreign key(fk필드명) refereneces 참조테이블명(PK필드명)
alter table employee add constraint employee_id primary key (id);
alter table employee add constraint employee_name Unique (name);

# [8] 제약조건 삭제
alter table employee drop constraint employee_id;

select * from employee; 	-- 특정한 테이블의 레코드 정보 확인(속성값 확인)

