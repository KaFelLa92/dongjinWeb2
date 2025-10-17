CREATE USER 'dev1'@'localhost' IDENTIFIED BY '1234'; 	-- 로컬 전용
create user 'dev2'@'%' identified by 'abcd'; 			-- 외부 접속 모두 허용
# 이제 홈 화면에서 상기 조건으로 다른 계정 만들고 접속 가능

# [3] 계정 권한 부여 ( grant ) , *(와일드카드) : 전체 뜻한다.
grant all privileges on springweb2.* to 'dev1'@'localhost';
grant select on springweb2.student to 'dev2'@'%'; # 조회 권한만 부여

# show grants for '계정명'@'도메인';
show grants for 'dev1'@'localhost';

# [4] 계정 권한 취소/회수 ( revoke ) 
# revoke 회수명령어 on 데이터베이스명.테이블명/뷰명 from '계정명'@'도메인';
revoke select on springweb2.student from 'dev2'@'%'; # 권한 뺏기

# [5] 계정의 비밀번호 생성
# alter user '계정명'@'도메인' identified by '새로운비밀번호';
alter user 'dev2'@'%' identified by '1234';
# 워크벤치 (DB를 편하게 사용할 수 있게 도와주는 소프트웨어)