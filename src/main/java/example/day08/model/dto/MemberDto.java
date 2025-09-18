package example.day08.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class MemberDto {
    private int mno;
    @NotBlank(message = "성명을 입력하세요.")
    private String name;
    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$" , message = "010-xxxx-xxxx 형식으로 입력해주세요.")
    private String phone;
    @Min(value = 0 , message = "연령은 음수가 될 수 없습니다.")
    @Max(value = 120 , message = "올바른 연령을 입력해주세요.")
    private int age;
}
