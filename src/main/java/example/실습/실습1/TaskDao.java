package example.실습.실습1;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskDao {

    /*
        [ 조건 ]
       1. 최소 클래스 파일을 사용하여 구현 : AppStart , TaskService , TaskDao
       2. 매 30초마다 모든 제품의 재고는 3개씩 감소한다.
       3. 매 1분마다 모든 제품 정보를 조회/출력 한다. *console 화면에 모든 제품 정보가 보이도록*
       4. 매 5분마다 재고가 10 이하인 상품의 재고를 +20개 추가한다.
     */

    // [DB연동 부가정보 ]
    private String db_url = "jdbc:mysql://localhost:3306/springweb2";
    private String db_user = "root";
    private String db_password = "1234";

    // [DB연동 멤버변수] * 하위클래스를 사용할수 있게 public 으로 사용.
    public Connection conn;

    // [DB연동 생성자] * 싱글톤이 아니다.
    public TaskDao() {
        connect();
    }

    // [DB연동 메소드]
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url, db_user, db_password);
            System.out.println("TaskDao.connect"); // 콘솔 확인용
        } catch (Exception e) {
            System.out.println(e);
        }
    }   // func end


    public void decreaseStock() {
        try {
            String sql = "update products set stock_quantity = stock_quantity -3 ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } // catch end
    } // func end

    public List<Map<String , String>> printAllProducts() {
        List<Map<String, String>> list = new ArrayList<>(); // 리스트 try catch 밖에서 호출
        try {
            String sql = "select * from products ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Map<String , String> map = new HashMap<>();
                map.put("product_id" , String.valueOf(rs.getInt("product_id")));
                map.put("product_name" , rs.getString("product_name"));
                map.put("stock_quantity" , String.valueOf(rs.getInt("stock_quantity")));
                list.add(map);
            }
        } catch (Exception e) {
            System.out.println(e);
        } // catch end
        return list;
    } // func end

    public void increaseStock() {
        try {
            String sql = "update products set stock_quantity = stock_quantity +20 where stock_quantity < 10 ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } // catch end
    } // func end


} // class end
