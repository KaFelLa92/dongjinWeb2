package example.실습.실습3;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper {

    // [1] 대출 요청 (재고가 0보다 커야 발동. DB에서 unsigned로 이중 유효성검사)
    @Update("update books set stock = (stock - 1) where id = #{bookId} and stock > 0 ")
    int checkoutBook(int bookId);

    // [2] 대출 기록 등록
    @Insert("insert into rentals (book_id , member) values (#{bookId} , #{member}) ")
    int checkoutRecord(int bookId, String member);

    // [3] 반납 요청
    @Update("update books set stock = (stock + 1) where id = #{bookId} ")
    int turnbackBook(int bookId);

    // [4] 대출 기록 변경 ( return_date가 null이어야 반응하도록 유효성 검사)
    @Update("update rentals set return_date = now() where id = #{rentId} and return_date is null ")
    int turnbackRecord(int rentId);
}
