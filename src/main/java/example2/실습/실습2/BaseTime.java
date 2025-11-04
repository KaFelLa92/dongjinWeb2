package example2.실습.실습2;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 엔티티 상속
@EntityListeners(AuditingEntityListener.class) // 해당 엔티티 자동으로 감시 적용
public class BaseTime {

    /*
    [조건 2] BaseTime 설계 :  모든 엔티티의 생성일·수정일을 자동으로 기록하기 위한 상속 전용 클래스이다.
    다음과 같은 조건으로 클래스를 작성한다.
    1. @MappedSuperclass : 엔티티 상속용 클래스 지정
    2. @EntityListeners(AuditingEntityListener.class) : JPA 감사 기능 활성화
    3. @CreatedDate, @LastModifiedDate : insert / update 시 자동 시간 기록
    4. 엔티티에서 extends BaseTime 으로 상속받아 사용한다.
     */

    @CreatedDate    // 현재 날짜/시간을 자동 주입
    private LocalDateTime createdAt; // 생성날짜/시간

    @LastModifiedDate   // 엔티티 변화 시점의 날짜/시간을 자동 주입
    private LocalDateTime updatedAt; // 수정날짜/시간

}
