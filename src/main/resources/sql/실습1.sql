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
        
        select * from products;