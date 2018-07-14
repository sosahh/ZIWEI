package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.HealthNews;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthNewsMapper {

    int insertHealthNews(HealthNews healthNews);

    int updateByPrimaryKey(HealthNews healthNews);

    //查询健康知识
    List<HealthNews> selectHealthNews(@Param("categoryId")Integer categoryId,@Param("type")Integer type);

    //查询健康知识详情
    List<HealthNews> selectHealthNewsBy(@Param("id")Integer id);

    //我收藏的健康知识
    List<HealthNews> selectHealthNewsBySC(@Param("uId")Long uId);

    //我评论的健康知识
    List<HealthNews> selectHealthNewsByPL(@Param("uId")Long uId);

    //我点赞的健康知识
    List<HealthNews> selectHealthNewsByDZ(@Param("uId")Long uId);

    //我分享的健康知识
    List<HealthNews> selectHealthNewsByFX(@Param("uId")Long uId);

    //后台查询健康知识列表
    List<HealthNews> getHealthNewsList(@Param("categoryId")Integer categoryId,@Param("recommend")Integer recommend);

    //后台查询健康知识详情
    List<HealthNews> getHealthNewsById(@Param("id")Integer id);

    //推荐
    int updateRecommend(@Param("recommend")Integer recommend,
                        @Param("recommendBF")Integer recommendBF,@Param("id")Integer id);

}