package example.실습.실습3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RentalsDto {
    private int id;
    private int book_id;
    private String member;
    private String rent_date;
    private String return_date;
}
