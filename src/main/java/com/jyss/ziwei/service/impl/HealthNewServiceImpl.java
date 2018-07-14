package com.jyss.ziwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.*;
import com.jyss.ziwei.service.HealthNewService;
import com.jyss.ziwei.service.PointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HealthNewServiceImpl implements HealthNewService {

    @Autowired
    private HealthNewsCategoryMapper newsCategoryMapper;
    @Autowired
    private HealthNewsMapper healthNewsMapper;
    @Autowired
    private UserCollectionMapper collectionMapper;
    @Autowired
    private UserPraiseMapper userPraiseMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Autowired
    private UserMapper userMapper;



    /**
     * 查询所有类目
     */
    @Override
    public ResponseResult selectNewsCategory() {
        List<HealthNewsCategory> categories = newsCategoryMapper.selectNewsCategory();
        Map<String, Object> map = new HashMap<>();
        map.put("categories",categories);
        return ResponseResult.ok(map);
    }


    /**
     * 查询所有滑动推荐
     */
    @Override
    public ResponseResult selectRecommend() {
        List<HealthNews> newsList = healthNewsMapper.getHealthNewsList(null, 2);
        if(newsList != null && newsList.size() > 0){
            Map<String, Object> map = new HashMap<>();
            map.put("list",newsList);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","暂无推荐！");
    }


    /**
     * 根据类目查询健康知识
     */
    @Override
    public ResponseResult selectHealthNews(Long uId, Integer categoryId, Integer page, Integer pageSize) {
        //判断是否都可查看
        Integer type = 1;
        if(uId == null){
            type = 1;
        }else{
            List<User> users = userMapper.selectUserByTel(uId, null);
            if(users != null && users.size() == 1){
                Integer level = users.get(0).getLevel();
                if(level > 0){
                    type = null;
                }else{
                    type = 1;
                }
            }else{
                type = 1;
            }
        }
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNews(categoryId, type);
        for (HealthNews healthNew : healthNews) {
            if(uId == null){
                healthNew.setIsCollection(0);
                healthNew.setIsPraise(0);
            }else {
                //是否收藏
                List<UserCollection> collections = collectionMapper.selectUserCollection(uId, healthNew.getId());
                if(collections != null && collections.size() > 0){
                    healthNew.setIsCollection(1);
                }else{
                    healthNew.setIsCollection(0);
                }
                //是否点赞
                List<UserPraise> userPraises = userPraiseMapper.selectUserPraise(uId, healthNew.getId());
                if(userPraises != null && userPraises.size() > 0){
                    healthNew.setIsPraise(1);
                }else{
                    healthNew.setIsPraise(0);
                }
            }
        }
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 健康知识详情
     */
    @Override
    public ResponseResult selectHealthNewsBy(Long uId, Integer knowId, Integer page, Integer pageSize) {
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNewsBy(knowId);
        if(healthNews != null && healthNews.size() > 0){
            Map<String, Object> map = new HashMap<>();
            map.put("news",healthNews.get(0));

            PageHelper.startPage(page,pageSize);
            List<UserComment> userComments = userCommentMapper.selectUserComment(knowId);
            PageInfo<UserComment> pageInfo = new PageInfo<>(userComments);
            Page<UserComment> comments = new Page<>(pageInfo);
            map.put("comments",comments);

            if(uId == null){
                map.put("IsCollection",0);
                map.put("IsPraise",0);
            }else {
                //是否收藏
                List<UserCollection> collections = collectionMapper.selectUserCollection(uId, knowId);
                if(collections != null && collections.size() > 0){
                    map.put("IsCollection",1);
                }else{
                    map.put("IsCollection",0);
                }
                //是否点赞
                List<UserPraise> userPraises = userPraiseMapper.selectUserPraise(uId, knowId);
                if(userPraises != null && userPraises.size() > 0){
                    map.put("IsPraise",1);
                }else{
                    map.put("IsPraise",0);
                }
            }
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","查询失败！");
    }


    /**
     * 我收藏的健康知识
     */
    @Override
    public ResponseResult selectHealthNewsBySC(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNewsBySC(uId);
        for (HealthNews healthNew : healthNews) {
            //是否收藏
            List<UserCollection> collections = collectionMapper.selectUserCollection(uId, healthNew.getId());
            if(collections != null && collections.size() > 0){
                healthNew.setIsCollection(1);
            }else{
                healthNew.setIsCollection(0);
            }
            //是否点赞
            List<UserPraise> userPraises = userPraiseMapper.selectUserPraise(uId, healthNew.getId());
            if(userPraises != null && userPraises.size() > 0){
                healthNew.setIsPraise(1);
            }else{
                healthNew.setIsPraise(0);
            }
        }
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 我评论的健康知识
     */
    @Override
    public ResponseResult selectHealthNewsByPL(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNewsByPL(uId);
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 我点赞的健康知识
     */
    @Override
    public ResponseResult selectHealthNewsByDZ(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNewsByDZ(uId);
        for (HealthNews healthNew : healthNews) {
            //是否收藏
            List<UserCollection> collections = collectionMapper.selectUserCollection(uId, healthNew.getId());
            if(collections != null && collections.size() > 0){
                healthNew.setIsCollection(1);
            }else{
                healthNew.setIsCollection(0);
            }
            //是否点赞
            List<UserPraise> userPraises = userPraiseMapper.selectUserPraise(uId, healthNew.getId());
            if(userPraises != null && userPraises.size() > 0){
                healthNew.setIsPraise(1);
            }else{
                healthNew.setIsPraise(0);
            }
        }
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 我分享的健康知识
     */
    @Override
    public ResponseResult selectHealthNewsByFX(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.selectHealthNewsByFX(uId);
        for (HealthNews healthNew : healthNews) {
            //是否收藏
            List<UserCollection> collections = collectionMapper.selectUserCollection(uId, healthNew.getId());
            if(collections != null && collections.size() > 0){
                healthNew.setIsCollection(1);
            }else{
                healthNew.setIsCollection(0);
            }
            //是否点赞
            List<UserPraise> userPraises = userPraiseMapper.selectUserPraise(uId, healthNew.getId());
            if(userPraises != null && userPraises.size() > 0){
                healthNew.setIsPraise(1);
            }else{
                healthNew.setIsPraise(0);
            }
        }
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }



    //////////后台部分////////

    /**
     * 添加类目
     */
    @Override
    public int insert(HealthNewsCategory healthNewsCategory) {
        return newsCategoryMapper.insert(healthNewsCategory);
    }


    /**
     * 修改类目
     */
    @Override
    public int updateByPrimaryKey(HealthNewsCategory healthNewsCategory) {
        return newsCategoryMapper.updateByPrimaryKey(healthNewsCategory);
    }


    /**
     * 添加健康知识
     */
    @Override
    public int insertHealthNews(HealthNews healthNews) {
        return healthNewsMapper.insertHealthNews(healthNews);
    }


    /**
     * 修改健康知识
     */
    @Override
    public int updateByPrimaryKey(HealthNews healthNews) {
        return healthNewsMapper.updateByPrimaryKey(healthNews);
    }


    /**
     * 后台查询健康知识列表
     */
    @Override
    public ResponseEntity getHealthNewsList(Integer categoryId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HealthNews> healthNews = healthNewsMapper.getHealthNewsList(categoryId,null);
        PageInfo<HealthNews> pageInfo = new PageInfo<>(healthNews);
        Page<HealthNews> result = new Page<>(pageInfo);
        return ResponseEntity.se(result);
    }


    /**
     * 后台查询健康知识详情和评论
     */
    @Override
    public ResponseEntity getHealthNewsById(Integer knowId, Integer page, Integer pageSize) {
        List<HealthNews> healthNews = healthNewsMapper.getHealthNewsById(knowId);
        if(healthNews != null && healthNews.size() > 0){
            Map<String, Object> map = new HashMap<>();
            map.put("news",healthNews.get(0));

            PageHelper.startPage(page,pageSize);
            List<UserComment> userComments = userCommentMapper.selectUserComment(knowId);
            PageInfo<UserComment> pageInfo = new PageInfo<>(userComments);
            Page<UserComment> comments = new Page<>(pageInfo);
            map.put("comments",comments);
            return ResponseEntity.se(map);
        }
        return ResponseEntity.op("-2","查询失败！");
    }


    /**
     * 推荐
     */
    @Override
    public int updateRecommend(Integer recommend, Integer recommendBF, Integer konwId) {
        return healthNewsMapper.updateRecommend(recommend,recommendBF,konwId);
    }


}
