package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.PhysicalTest;
import com.jyss.ziwei.mapper.PhysicalTestMapper;
import com.jyss.ziwei.service.PhysicalTestService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PhysicalTestServiceImpl implements PhysicalTestService{
    @Autowired
    private PhysicalTestMapper ptMapper;

    @Override
    public int insert(PhysicalTest physicalTest) {
       return ptMapper.insert(physicalTest);
    }

    @Override
    public int updateByPrimaryKey(PhysicalTest physicalTest) {
        return ptMapper.updateByPrimaryKey(physicalTest);
    }

    @Override
    public int deletePhysicalTest(@Param("id") String id) {
        return ptMapper.deletePhysicalTest(id);
    }

    @Override
    public List<PhysicalTest> getPhysicalTest(@Param("status") String status, @Param("uId") String uId, @Param("id") String id) {
        return ptMapper.getPhysicalTest(status,uId,id);
    }
}