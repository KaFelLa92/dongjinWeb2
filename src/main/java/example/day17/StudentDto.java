package example.day17;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class StudentDto{
    private int sno;
    private String name;
    private int kor;
    private int math;
}
