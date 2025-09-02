package example.실습.실습1;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {
    // DI
    final private TaskDao taskDao;

    /*
    [SPRING WEB2 ] 실습1 : 자동 재고 관리 시스템 , 스프링스케줄링 + JDBC DAO
        [ 조건 ]
           1. 최소 클래스 파일을 사용하여 구현 : AppStart , TaskService , TaskDao
           2. 매 30초마다 모든 제품의 재고는 3개씩 감소한다.
           3. 매 1분마다 모든 제품 정보를 조회/출력 한다. *console 화면에 모든 제품 정보가 보이도록*
           4. 매 5분마다 재고가 10 이하인 상품의 재고를 +20개 추가한다.
        [ 샘플DB ]
        DROP DATABASE IF EXISTS springweb2;
        CREATE DATABASE springweb2;
        USE springweb2;
        CREATE TABLE products (
            product_id INT PRIMARY KEY AUTO_INCREMENT, -- 상품 ID (자동 증가)
            product_name VARCHAR(255) NOT NULL,        -- 상품명
            stock_quantity INT NOT NULL                -- 재고 수량
        );

        INSERT INTO products (product_name, stock_quantity) VALUES
        ('무선 이어폰', 25),
        ('스마트워치', 12),
        ('게이밍 키보드', 30),
        ('기계식 마우스', 8),
        ('휴대용 충전기', 15);

[ 제출방법 ] 코드가 작성된 파일이 위치한 깃허브 상세 주소를 제출하시오.
     */

    // @Scheduled( fixedRate = 30000 ) // 30초
    @Scheduled(cron = "*/30 * * * * *")
    public void decreaseStock() {
        System.out.println("[30초] TaskService.decreaseStock");
        taskDao.decreaseStock();
    }

    @Scheduled(cron = "0 */1 * * * *") // 매 1분마다
    public void printAllProducts() {
        List<Map<String, String>> lists = taskDao.printAllProducts();
        System.out.println("[1분] TaskService.printAllProducts");
        for (Map<String, String> list : lists) {
            System.out.println("제품번호 : " + list.get("product_id") + "\n제품명 : " + list.get("product_name") + "\n현재 수량 : " + list.get("stock_quantity") + "\n");
        }
    }

    @Scheduled(cron = "0 */5 * * * *") // 매 5분마다
    public void increaseStock() {
        System.out.println("[5분] TaskService.increaseStock");
        taskDao.increaseStock();
    }

} // class end
