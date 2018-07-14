package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserCollection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCollectionMapper {

    int insert(UserCollection userCollection);

    int updateByPrimaryKey(UserCollection userCollection);

    //查询收藏
    List<UserCollection> selectUserCollection(@Param("uId")Long uId, @Param("knowId")Integer knowId);

}