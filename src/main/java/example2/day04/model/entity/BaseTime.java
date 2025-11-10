package example2.day04.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter // 롬복 getter
@MappedSuperclass // 엔티티 상속 용도.  AppStart 클래스 위에 @EnableJpaAuditing 주입해야 가능함 . JPA가 데이터베이스를 모니터링(Auditing감시)하여 변화되면 수정날짜/시간 주입
@EntityListeners(AuditingEntityListener.class) // 해당 엔티티를 자동으로 감시 적용
public class BaseTime {

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

}
