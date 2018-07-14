package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.BConstant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BConstantService {
    int insert(BConstant bConstant);

    int updateByPrimaryKey(BConstant bConstant);

    int deleteConstant(@Param("id") String id);

    List<BConstant> getConstant(@Param("status") String status, @Param("bzType") String bzType,
                               @Param("bzId") String bzId, @Param("pId") String pId, @Param("bzInfo") String bzInfo);

    List<BConstant> selectBConstant(@Param("bzType") String bzType,@Param("bzId") String bzId);
}