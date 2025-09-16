package example.day07.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class BoardDto {
    private int bno;
    @NotBlank(message = "본문 내용을 입력하세요.")
    private String bcontent;
    @NotBlank(message = "작성자를 입력하세요.")
    @Size(max = 10, message = "작성자 이름은 10자 이하로 작성해주세요.")
    private String bwriter;

}
