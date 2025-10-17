use springweb2; -- DB 선택
set SQL_SAFE_UPDATES = 0; -- update 사용 설정

# 트랜잭션 - 여러 작업을 하나의 묶음으로 간주하여, 모두 성공하면 commit, 하나라도 실패하면 rollback
set autocommit = 0; -- mysql 워크벤치에서 자동 commit 비활성화 설정 (학습용)

# 1. 트랜잭션 시작
start transaction;
# 2. 여러 작업 (DML:insert/update/delete만 가능하다) , DDL불가능(alter, create, drop)
update trans set money = money - 30000 where name = "신동엽"; -- 출금
update trans set money = money + 30000 where name = "서장훈"; -- 입금
# 3. 되돌리기(취소)
rollback;
# 4. 완료
commit;
# 5. 확인 : (1) 1 -> 2 -> 4(commit) -> 5 (2) 1 -> 2 -> 3(rollback) -> 5
select * from trans;

# [2] 
# 1. 트랜잭션 시작
start transaction;
# 2. 여러 작업 1 
update trans set money = money - 30000 where name = "신동엽"; -- 출금
# 3. 저장 지점 만들기
savepoint pointA; -- A저장지점
# 4. 여러 작업 2
update trans set money = money - 30000 where name = "서장훈"; -- 출금
# 5. 완료
commit; 
# 6. 특정한 지점으로 롤백
rollback to pointA; -- 저장 지점까지 롤백(이동)
# 7. 확인 : (1) 7-1-2-3-4-5-7 : 둘 다 출금 적용 (2) 7-1-2-3-4-6(특정롤백)-5-7 : 신동엽만 출금 적용
select * from trans;

# DDL DML DCL (권한부여/트랜잭션TCL)
# TCL (commit, rollbak, savepoint )
# 1. JAVA SPRING ( @Transactional 사용하되 RuntimeException으로 롤백한다. savepoint 지원하지 않는다 )
# 2. JAVA JDBC(DAO) : String sql = "savepoint"

# [3]
start transaction;
# (1)
update trans set money = money - 10000 where name = "유재석";
savepoint step1; 

# (2)
update trans set money = money - 10000 where name = "서장훈";
savepoint step2; 

# (3)
update trans set money = money - 10000 where name = "강호동";
savepoint step3; 

rollback to step1; commit; # (1)까지 반영
rollback to step2; commit; # (1)(2)까지 반영 
rollback to step3; commit; # (1)(2)(3)까지 반영

# 실무 : 신입/주니어개발자 , 데이터베이스 수정 권한 , view(프론트엔드/가상테이블)




