package example2.day03._자바참조;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
public class Category {

    // 1. 멤버변수
    private int cno;        // 카테고리넘버
    private String cname;   // 카테고리제목
    @ToString.Exclude       // ToString 함수 제외 (순환참조 방지)
    List<Board> boardList
            = new ArrayList<>();
    // board 여러개를 참조하여 **양방향** (오버스택 걸림)

    // 2. 생성자

    // 3. 메소드


} // class end
