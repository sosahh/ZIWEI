package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserShare;
import org.springframework.stereotype.Repository;

@Repository
public interface UserShareMapper {

    int insert(UserShare userShare);

    int updateByPrimaryKey(UserShare userShare);
}