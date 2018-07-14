package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.FeedBack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackMapper {

    int insert(FeedBack feedback);

    int updateByPrimaryKey(FeedBack feedback);

    List<FeedBack> selectFeedBack(@Param("uId")Long uId);

    int updateFeedStatus(@Param("uId")Long uId);

    List<FeedBack> selectFeedBackHT(@Param("manageId")Long manageId, @Param("replyStatus")Integer replyStatus);


}