package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BConstant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BConstantMapper {

    int insert(BConstant bConstant);

    int updateByPrimaryKey(BConstant bConstant);

    int deleteConstant(@Param("id") String id);

    List<BConstant> getConstant(@Param("status") String status, @Param("bzType") String bzType,
                               @Param("bzId") String bzId, @Param("pId") String pId, @Param("bzInfo") String bzInfo);

    //常量查询
    List<BConstant> selectBConstant(@Param("bzType") String bzType,@Param("bzId") String bzId);


    BConstant selectBConstantBy(@Param("bzType") String bzType,@Param("bzId") String bzId);

}