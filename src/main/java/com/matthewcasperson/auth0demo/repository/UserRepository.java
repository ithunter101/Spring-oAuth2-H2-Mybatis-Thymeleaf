package com.matthewcasperson.auth0demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.matthewcasperson.auth0demo.model.Users;

import java.util.List;

@Mapper
public interface UserRepository {
    @Select("select * from users")
    public List <Users> findAll();

    @Select("select user_name from users where email=#{email}")
    public String findByEmail(String email);

    @Insert("insert into users (user_name, email) values (#{userName}, #{email})")
    public int insert(String userName, String email);

    @Delete("DELETE FROM users WHERE email=#{email}")
    public int deleteByEmail(String email);

    @Update("UPDATE users set user_name=#{userName} where email=#{email}")
    public int update(String userName, String email);
}
