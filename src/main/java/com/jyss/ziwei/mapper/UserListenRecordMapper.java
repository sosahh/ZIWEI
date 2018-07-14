package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserListenRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserListenRecordMapper {

    int insert(UserListenRecord userListenRecord);

    int updateByPrimaryKey(UserListenRecord userListenRecord);

    //查询视频历史
    List<UserListenRecord> selectUserListen(@Param("uId")Long uId);

    //插入或更新
    int updateUserListen(UserListenRecord userListenRecord);


}