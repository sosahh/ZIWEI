package com.jyss.ziwei.service;


import com.jyss.ziwei.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    //条件查询
    List<User> selectUserByTel(@Param("uId")Long uId,@Param("telephone")String telephone);

    //用户注册
    ResponseResult insertUser(@Param("telephone")String telephone,@Param("password")String password);

    //用户登陆
    ResponseResult selectUser(@Param("telephone")String telephone,@Param("password")String password);

    //更新用户
    int updateByPrimaryKey(User user);

    //查询用户目标
    ResponseResult selectUserTarget(@Param("uId")Long uId);


    //查询视频历史
    ResponseResult selectUserListen(@Param("uId")Long uId, @Param("page")Integer page,
                                    @Param("pageSize")Integer pageSize);

    //插入或更新视频
    ResponseResult updateUserListen(UserListenRecord userListenRecord);

    //用户积分明细
    ResponseResult selectUserPoints(@Param("uId")Long uId, @Param("page")Integer page,
                                    @Param("pageSize")Integer pageSize);


    /////////////后台///////////////
    ResponseEntity selectAllUser(@Param("manageId")Long manageId, @Param("page")Integer page,
                                 @Param("pageSize")Integer pageSize);

    //条件搜索用户
    ResponseEntity selectLikeUser(@Param("telephone")String telephone, @Param("manageId")Long manageId);


    //插入或更新目标
    ResponseEntity updateUserTarget(UserTarget userTarget);


}
