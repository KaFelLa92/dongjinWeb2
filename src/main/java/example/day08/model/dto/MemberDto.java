package example.day08.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class MemberDto {
    private int mno;
    private String name;
    private String phone;
    private int age;
}
