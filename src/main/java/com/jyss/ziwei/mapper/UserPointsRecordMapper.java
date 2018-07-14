package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserPointsRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointsRecordMapper {

    int insert(UserPointsRecord userPointsRecord);

    int updateByPrimaryKey(UserPointsRecord userPointsRecord);

    //查询积分明细
    List<UserPointsRecord> selectUserPoints(@Param("uId")Long uId, @Param("bzType")Integer bzType,
                                            @Param("createdAt")String createdAt);


}