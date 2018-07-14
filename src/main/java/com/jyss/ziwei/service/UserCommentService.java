package com.jyss.ziwei.service;


import com.jyss.ziwei.entity.ResponseEntity;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;

public interface UserCommentService {

    //收藏
    ResponseResult insertUserCollection(@Param("uId")Long uId, @Param("knowId")Integer knowId);

    //取消收藏
    ResponseResult updateUserCollection(@Param("uId")Long uId, @Param("knowId")Integer knowId);

    //点赞
    ResponseResult insertUserPraise(@Param("uId")Long uId, @Param("knowId")Integer knowId);

    //取消点赞
    ResponseResult updateUserPraise(@Param("uId")Long uId, @Param("knowId")Integer knowId);

    //添加评论
    ResponseResult insertUserComment(@Param("uId")Long uId, @Param("knowId")Integer knowId,
                                     @Param("nickname")String nickname, @Param("content")String content);

    //添加分享
    ResponseResult insertUserShare(@Param("uId")Long uId, @Param("knowId")Integer knowId,
                                   @Param("type")Integer type);

    //添加反馈
    ResponseResult insertFeedBack(@Param("uId")Long uId, @Param("nickname")String nickname,
                                  @Param("message")String message);

    //查询反馈
    ResponseResult selectFeedBack(@Param("uId")Long uId, @Param("page")Integer page,
                                  @Param("pageSize")Integer pageSize);

    //读取反馈消息
    ResponseResult updateFeedStatus(@Param("uId")Long uId);

    /////////////后台部分////////////
    //回复评论
    ResponseEntity updateUserComment(@Param("uId")Long uId, @Param("commentId")Integer commentId,
                                     @Param("content")String content);

    //删除评论
    ResponseEntity deleteUserComment(@Param("commentId")Integer commentId);

    //后台查询反馈
    ResponseEntity selectFeedBackHT(@Param("manageId")Long manageId, @Param("page")Integer page,
                                    @Param("pageSize")Integer pageSize, @Param("replyStatus")Integer replyStatus);

    //回复反馈
    ResponseEntity updateFeedBack(@Param("id")Integer id, @Param("uId")Long uId, @Param("content")String content);

}
