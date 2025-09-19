package eval6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
public class AgoraDto {
    private int ano;
    private String acontent;
    private String apwd;
    private int cno; // 특정 영화에 작성된 토론글 조회용 참조키
}
