package example.day13;

import lombok.*;

import java.io.Serializable;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StudentDto {
    private int sno;
    private String name;
    private int kor;
    private int math;
}