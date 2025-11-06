package example2.day03._자바참조;

public class Example {
    public static void main(String[] args) {
        // 자바는 100% 객체지향(OOP) 언어
        System.out.println("출력");
        // 클래스는 설계도. 객체는 실제 도구들

        // JPA는 영속성 : 데이터베이스와 자바의 유사성
        // DB의 테이블 == 클래스 == 엔티티 클래스
        // DB 테이블 레코드 == 객체(인스턴스) == 엔티티객체

        // [1] 카테고리 2개 생성 , PK( 상위 테이블 )
        Category category1 = new Category();
        category1.setCno(1); category1.setCname("공지사항");
        Category category2 = new Category();
        category2.setCno(2); category2.setCname("자유게시판");

        // [2] 게시물 생성 , FK( 하위 테이블 )
        // 공지사항에 게시물 작성
        Board board1 = new Board();
        board1.setBno(1); board1.setBtitle("공지1");
        board1.setBcontent("공지내용");
        board1.setCategory(category1);  // 이 부분이 중요. 공지사항 카테고리를 참조
        // ** 1번 게시물에 1번(공지사항객체) 참조하기 **
        System.out.println( board1.getCategory().getCname() );

        // [3] 공지사항 데이터로 게시물 조회 , <양방향>
        // 공지사항 객체에 1번 게시물 대입
        category1.getBoardList().add(board1);
        System.out.println(category1.getBoardList()); // 스택오버플로우 걸림. 카테고리 어노테이션 손보기
        
        // * toString() 함수 : 객체 호출 시 참조주소값 대신에 참조문자열 반환
        // 즉] 인천광역시 부평구 101동 101호 --> 안방, 주방

        // [4] 상황1 : 1번 공지사항에 게시물 작성
        Board board2 = new Board();
        board2.setBno(2); board2.setBtitle("공지2");
        board2.setBcontent("공지내용2");
        board2.setCategory(category1);  // ** 단방향 참조 **
        // ** 양방향 참조 **
        category1.getBoardList().add(board2);
        System.out.println(category1.getBoardList());

        // 상황1 결과 : 카테고리로 게시물 조회 , 게시물로 카테고리 조회
        System.out.println(category1.getBoardList());
        System.out.println(board2.getCategory());

    } // main end
} // class end
