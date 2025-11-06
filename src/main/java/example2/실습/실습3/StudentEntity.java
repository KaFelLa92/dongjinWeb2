package example2.실습.실습3;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "neostudent")
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity extends BaseTime {

    /*
    [조건 1] 엔티티 설계 : “과정(Course)”, “학생(Student)”, “수강기록(Enroll)” 엔티티를 생성한다.
    - 각 엔티티의 주요 역할과 관계를 명확히 구분할 것.
    - JPA 어노테이션(@Entity, @Id, @GeneratedValue, @OneToMany, @ManyToOne, @JoinColumn 등)을 활용한다.
    - BaseTime을 상속받아 createdAt, updatedAt 자동 기록 기능을 포함한다.
    - Course : 과정번호(courseId, PK), 과정명(courseName)
    - Student : 학생번호(studentId, PK), 학생명(studentName)
    - Enroll : 수강번호(enrollId, PK), 수강상태(status), 과정번호(FK), 학생번호(FK)
    - 연관관계 : Course(1) ↔ Enroll(N) ↔ Student(1) 구조 설계
    (한 과정에는 여러 학생 등록 가능, 한 학생은 여러 과정 수강 가능)
     */

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int studentId;
    private String studentName;
    // 수강기록과 양방향 연결
    @OneToMany( mappedBy = "studentEntity") // 1:M , 하나 PK가 다수 FK 관리
    @ToString.Exclude
    @Builder.Default
    private List<EnrollEntity>
            enrollEntities = new ArrayList<>();
}
