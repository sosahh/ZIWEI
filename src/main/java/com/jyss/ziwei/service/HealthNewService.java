package com.jyss.ziwei.service;


import com.jyss.ziwei.entity.HealthNews;
import com.jyss.ziwei.entity.HealthNewsCategory;
import com.jyss.ziwei.entity.ResponseEntity;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface HealthNewService {

    ResponseResult selectNewsCategory();

    //查询所有滑动推荐
    ResponseResult selectRecommend();

    //根据类目查询健康知识
    ResponseResult selectHealthNews(@Param("uId")Long uId, @Param("categoryId")Integer categoryId,
                                    @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    //健康知识详情
    ResponseResult selectHealthNewsBy(@Param("uId")Long uId, @Param("knowId")Integer knowId,
                                      @Param("page")Integer page, @Param("pageSize")Integer pageSize);


    //我收藏的健康知识
    ResponseResult selectHealthNewsBySC(@Param("uId")Long uId, @Param("page")Integer page,
                                        @Param("pageSize")Integer pageSize);


    //我评论的健康知识
    ResponseResult selectHealthNewsByPL(@Param("uId")Long uId, @Param("page")Integer page,
                                        @Param("pageSize")Integer pageSize);


    //我点赞的健康知识
    ResponseResult selectHealthNewsByDZ(@Param("uId")Long uId, @Param("page")Integer page,
                                        @Param("pageSize")Integer pageSize);


    //我分享的健康知识
    ResponseResult selectHealthNewsByFX(@Param("uId")Long uId, @Param("page")Integer page,
                                        @Param("pageSize")Integer pageSize);



    //////////后台部分///////////
    //添加类目
    int insert(HealthNewsCategory healthNewsCategory);

    //修改类目
    int updateByPrimaryKey(HealthNewsCategory healthNewsCategory);

    //添加健康知识
    int insertHealthNews(HealthNews healthNews);

    //修改健康知识
    int updateByPrimaryKey(HealthNews healthNews);

    //后台查询健康知识列表
    ResponseEntity getHealthNewsList(@Param("categoryId")Integer categoryId,@Param("page")Integer page,
                                     @Param("pageSize")Integer pageSize);

    //后台查询健康知识详情和评论
    ResponseEntity getHealthNewsById(@Param("knowId")Integer knowId,@Param("page")Integer page,
                                     @Param("pageSize")Integer pageSize);

    //推荐
    int updateRecommend(@Param("recommend")Integer recommend,
                        @Param("recommendBF")Integer recommendBF,@Param("konwId")Integer konwId);

}
