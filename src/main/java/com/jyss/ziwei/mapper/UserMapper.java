package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    int insert(User user);

    //注册
    int insertUser(User user);

    int updateByPrimaryKey(User user);

    //条件查询
    List<User> selectUserByTel(@Param("uId")Long uId, @Param("telephone")String telephone);

    //后台条件查询
    List<User> selectUserByHT(@Param("manageId")Long manageId);

    //手机模糊查询
    List<User> selectUserBy(@Param("telephone")String telephone, @Param("manageId")Long manageId);

}