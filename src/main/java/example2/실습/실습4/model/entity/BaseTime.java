package example2.실습.실습4.model.entity;

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
    [조건1] 엔티티 설계
    - BaseTime : 생성일(createdAt), 수정일(updatedAt)을 자동 저장
     */

    @CreatedDate
    private LocalDateTime creaedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
