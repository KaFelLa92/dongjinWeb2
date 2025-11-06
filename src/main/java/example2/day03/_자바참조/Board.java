package example2.day03._자바참조;

import lombok.Data;

@Data
public class Board {
    
    // 1. 멤버변수
    private int bno;            // 게시물번호
    private String btitle;      // 게시물제목
    private String bcontent;    // 게시물내용
    private Category category;  // FK **단방향** 참조

    // 2. 생성자
    
    // 3. 메소드

} // class end
