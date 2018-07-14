package com.jyss.ziwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.*;
import com.jyss.ziwei.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserCommentServiceImpl implements UserCommentService {

    @Autowired
    private UserCollectionMapper collectionMapper;
    @Autowired
    private UserPraiseMapper userPraiseMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Autowired
    private UserShareMapper userShareMapper;
    @Autowired
    private FeedBackMapper feedBackMapper;


    /**
     * 收藏
     */
    @Override
    public ResponseResult insertUserCollection(Long uId, Integer knowId) {
        List<UserCollection> userCollections = collectionMapper.selectUserCollection(uId, knowId);
        if(userCollections == null || userCollections.size() == 0){
            UserCollection collection = new UserCollection();
            collection.setuId(uId);
            collection.setKnowId(knowId);
            collection.setStatus(1);
            collection.setCreated(new Date());
            int count = collectionMapper.insert(collection);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","收藏失败！");
        }
        return ResponseResult.error("-2","操作异常！");
    }


    /**
     * 取消收藏
     */
    @Override
    public ResponseResult updateUserCollection(Long uId, Integer knowId) {
        List<UserCollection> userCollections = collectionMapper.selectUserCollection(uId, knowId);
        if(userCollections != null && userCollections.size() > 0){
            UserCollection collection = new UserCollection();
            collection.setId(userCollections.get(0).getId());
            collection.setStatus(0);
            int count = collectionMapper.updateByPrimaryKey(collection);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","取消收藏失败！");
        }
        return ResponseResult.error("-2","操作异常！");
    }


    /**
     * 点赞
     */
    @Override
    public ResponseResult insertUserPraise(Long uId, Integer knowId) {
        List<UserPraise> praises = userPraiseMapper.selectUserPraise(uId, knowId);
        if(praises == null || praises.size() == 0){
            UserPraise userPraise = new UserPraise();
            userPraise.setuId(uId);
            userPraise.setKnowId(knowId);
            userPraise.setStatus(1);
            userPraise.setCreated(new Date());
            int count = userPraiseMapper.insert(userPraise);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","点赞失败！");
        }
        return ResponseResult.error("-2","操作异常！");
    }


    /**
     * 取消点赞
     */
    @Override
    public ResponseResult updateUserPraise(Long uId, Integer knowId) {
        List<UserPraise> praises = userPraiseMapper.selectUserPraise(uId, knowId);
        if(praises != null && praises.size() > 0){
            UserPraise userPraise = new UserPraise();
            userPraise.setId(praises.get(0).getId());
            userPraise.setStatus(0);
            int count = userPraiseMapper.updateByPrimaryKey(userPraise);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","取消点赞失败！");
        }
        return ResponseResult.error("-2","操作异常！");
    }


    /**
     * 添加评论
     */
    @Override
    public ResponseResult insertUserComment(Long uId, Integer knowId, String nickname, String content) {
        UserComment comment = new UserComment();
        comment.setuId(uId);
        comment.setKnowId(knowId);
        comment.setuNick(nickname);
        comment.setContent(content);
        comment.setStatus(1);
        comment.setCreated(new Date());
        int count = userCommentMapper.insert(comment);
        if(count == 1){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","评论失败！");
    }


    /**
     * 添加分享
     */
    @Override
    public ResponseResult insertUserShare(Long uId, Integer knowId, Integer type) {
        UserShare userShare = new UserShare();
        userShare.setuId(uId);
        userShare.setKnowId(knowId);
        userShare.setType(type);
        userShare.setStatus(1);
        userShare.setCreated(new Date());
        int count = userShareMapper.insert(userShare);
        if(count == 1){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","操作失败！");
    }


    /**
     * 添加反馈
     */
    @Override
    public ResponseResult insertFeedBack(Long uId, String nickname, String message) {
        FeedBack feedBack = new FeedBack();
        feedBack.setuId(uId);
        feedBack.setuNick(nickname);
        feedBack.setMessage(message);
        feedBack.setStatus(1);
        feedBack.setCreateTime(new Date());
        int count = feedBackMapper.insert(feedBack);
        if(count == 1){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","操作失败！");
    }


    /**
     * 查询反馈
     */
    @Override
    public ResponseResult selectFeedBack(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<FeedBack> feedBacks = feedBackMapper.selectFeedBack(uId);
        PageInfo<FeedBack> pageInfo = new PageInfo<>(feedBacks);
        Page<FeedBack> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 读取反馈消息
     */
    @Override
    public ResponseResult updateFeedStatus(Long uId) {
        feedBackMapper.updateFeedStatus(uId);
        return ResponseResult.ok(new HashMap<>());

    }


    /////////////后台部分////////////

    /**
     * 回复评论
     */
    @Override
    public ResponseEntity updateUserComment(Long uId, Integer commentId, String content) {
        UserComment comment = new UserComment();
        comment.setId(commentId);
        comment.setReplyId(uId);
        comment.setReplyContent(content);
        comment.setReplyStatus(1);
        comment.setReplyCreated(new Date());
        int count = userCommentMapper.updateByPrimaryKey(comment);
        if(count == 1){
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("-2","回复失败！");

    }


    /**
     * 删除评论
     */
    @Override
    public ResponseEntity deleteUserComment(Integer commentId) {
        UserComment comment = new UserComment();
        comment.setId(commentId);
        comment.setStatus(0);
        int count = userCommentMapper.updateByPrimaryKey(comment);
        if(count == 1){
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("-2","删除失败！");
    }


    /**
     * 查询反馈
     */
    @Override
    public ResponseEntity selectFeedBackHT(Long manageId, Integer page, Integer pageSize, Integer replyStatus) {
        PageHelper.startPage(page,pageSize);
        List<FeedBack> feedBacks = feedBackMapper.selectFeedBackHT(manageId, replyStatus);
        PageInfo<FeedBack> pageInfo = new PageInfo<>(feedBacks);
        Page<FeedBack> result = new Page<>(pageInfo);
        return ResponseEntity.se(result);
    }


    /**
     * 回复反馈
     */
    @Override
    public ResponseEntity updateFeedBack(Integer id, Long uId, String content) {
        FeedBack feedBack = new FeedBack();
        feedBack.setId(id);
        feedBack.setStatus(0);
        feedBack.setReplyId(uId);
        feedBack.setReplyContent(content);
        feedBack.setReplyCreated(new Date());
        feedBack.setReplyStatus(1);
        int count = feedBackMapper.updateByPrimaryKey(feedBack);
        if(count == 1){
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("-2","回复失败！");
    }


}
