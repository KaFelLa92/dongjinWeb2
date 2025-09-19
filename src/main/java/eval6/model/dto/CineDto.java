package eval6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
public class CineDto {
    private int cno;
    private String ctitle;
    private String cdirector;
    private String cgenre;
    private String ccontent;
    private String cpwd;
}
