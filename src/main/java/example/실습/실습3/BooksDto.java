package example.실습.실습3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class BooksDto {
    private int id;
    private String title;
    private int stock;
}
