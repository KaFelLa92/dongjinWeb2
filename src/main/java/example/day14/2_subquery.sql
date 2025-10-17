use springweb2;
select * from student;

# [1] SELECT 중첩 : 서브쿼리
# 1. 일반 select
select avg( (kor+math) / 2 ) 평균점수 from student;
# 2. 중첩 select = 서브쿼리 , 평균보다 높은 점수의 학생명
# select (서브쿼리) from (서브쿼리) where (서브쿼리)
select name from student 
	where (kor+math) / 2 > ( 
    select avg( (kor+math) / 2 ) 평균점수 from student
);

# [2] 국어 점수가 평균 이상인 학생들과 같은 점수를 가진 학생 조회
	# (1) 먼저 내부 쿼리(서브쿼리) 기준으로 작성한다. ( 서브쿼리 ( 서브쿼리 ( 서브쿼리 ) ) )
	# (2) 메인 쿼리를 작성한다.
select * from student;
select avg(kor) 국어평균점수 from student;
-- 평균보다 높은 국어점수
select kor from student where kor >= (select avg(kor) from student);
-- 평균 이상 점수를 가진 학생 조회
select name from student where kor in( 
	select kor from student where kor >= (select avg(kor) from student)
);

# [3] 서브쿼리 이용한 각 학생들과 총점 비교
select count(*) from student s2; -- 레코드 수
select
	s1.name , 
	( select count(*) from student s2 
		where (s2.kor+s2.math) > (s1.kor+s1.math)
    )
from student s1;

# [4] 서브쿼리로 평균점수 정렬
select name , 평균 
	from ( select name , (kor+math)/2 as 평균 from student ) as 평균테이블
	order by 평균 desc;

