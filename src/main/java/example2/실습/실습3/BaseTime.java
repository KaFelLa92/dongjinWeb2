package example2.실습.실습3;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    /*
    [조건 2] BaseTime 설계 : 모든 엔티티의 생성일·수정일을 자동으로 기록하기 위한 상속 전용 클래스이다.
    1. @MappedSuperclass : 엔티티 상속 전용 클래스 지정
    2. @EntityListeners(AuditingEntityListener.class) : JPA 감사(Auditing) 기능 활성화
    3. @CreatedDate, @LastModifiedDate : insert / update 시 자동 시간 기록
    4. 각 엔티티에서 extends BaseTime으로 상속받아 공통 필드 사용
     */

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}
