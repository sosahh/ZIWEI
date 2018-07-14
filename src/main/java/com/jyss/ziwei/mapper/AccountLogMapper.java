package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.AccountLog;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLogMapper {

    int insert(AccountLog accountLog);

    int updateByPrimaryKey(AccountLog accountLog);
    
}