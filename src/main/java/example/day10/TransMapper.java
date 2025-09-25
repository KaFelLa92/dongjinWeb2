package example.day10;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface TransMapper {
    // (1) insert 1 : 정상
    @Insert("insert into trans (name) values (#{name}) ")
    boolean trans1(String name);

    // (2) insert 2 : 비정상
    @Insert("insert into trans (오타) values (#{name}) ")
    boolean trans2(String name);

    // (3) update 입금 , 더하기
    @Update("update trans set money = money + #{money} where name = #{name}")
    boolean deposit(String name, int money);

    // (4) update 입금 , 빼기
    @Update("update trans set money = money - #{money} where name = #{name}")
    boolean withdraw(String name , int money);

}
