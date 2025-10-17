# 인덱스란? : 데이터를 빠르게 검색하기 위한 색인
# primary key는 기본적인 인덱스를 갖는다.
# 관계형 데이터베이스 구조상 특정한 데이터를 찾을 때 검색기준(인덱스)을 미리 지정하면 빠르다.

USE springweb2;

# [1] primary key 기본적인 인덱스를 갖는다.
select * from employee where id = 1;

# [2] 인덱스 목록 조회
# show index from 테이블명;
show index from employee;

# [3] 단일 컬럼 인덱스 생성
# create index 인덱스명 on 테이블명( 필드명 );
create index idx_name on employee( name ); # 확인 : 3 -> 2

# [4] 쿼리 성능 조회
explain analyze select * from employee where name = '유재석';

# [5] 인덱스 삭제
# drop index 인덱스명 on 테이블명;
drop index idx_name on employee; # 확인 : 5 -> 2 -> 4 , 3 -> 4

# [6] 복합 인덱스 생성
# create index idx_salary on employee( 필드명1 , 필드명2 );
create index idx_salary on employee( dept_id , salary );
# 주의할점 : 첫번째 인덱스에 대해서는 단일 사용 가능하다. 두번째 인덱스부터는 단일 사용 불가능.
explain analyze select * from employee where dept_id = 1;					-- 첫번째 인덱스 가능
explain analyze select * from employee where salary = 7000;					-- 두번째 인덱스 불가능
explain analyze select * from employee where dept_id = 1 and salary = 7000; -- 같이 인덱스 가능

# [7] join : PK - FK가 아니더라도 조인 가능하다.
# FK에 인덱스 추가
create index idx_dept on employee( dept_id );
# FK에 인덱스 추가 후 join하면 조인 속도가 증가한다. *
select * from employee e inner join department d on e.dept_id = d.dept_id;

# [8] 문자열 검색 : 자연어(사람이 사용하는 언어/번역속도 줄이기) VS 기계어(컴퓨터 2진수)
# type이 text, longtext, char, varchar 가능하다.
create fulltext index idx_name_full on employee(name);
explain analyze select * from employee where match(name) against('유재석'); -- name = '유재석'


select * from department;
select * from employee;





