package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.BConstant;
import com.jyss.ziwei.mapper.BConstantMapper;
import com.jyss.ziwei.service.BConstantService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BConstantServiceImpl implements BConstantService{
    @Autowired
    private BConstantMapper bcMapper;
    @Override
    public int insert(BConstant bConstant) {
        return bcMapper.insert(bConstant);
    }

    @Override
    public int updateByPrimaryKey(BConstant bConstant) {
        return bcMapper.updateByPrimaryKey(bConstant);
    }

    @Override
    public int deleteConstant(@Param("id") String id) {
        return bcMapper.deleteConstant(id);
    }

    @Override
    public List<BConstant> getConstant(@Param("status") String status, @Param("bzType") String bzType, @Param("bzId") String bzId, @Param("pId") String pId, @Param("bzInfo") String bzInfo) {
        return bcMapper.getConstant(status,bzType,bzId,pId,bzInfo);
    }

    @Override
    public List<BConstant> selectBConstant(String bzType, String bzId) {
        return bcMapper.selectBConstant(bzType, bzId);
    }
}