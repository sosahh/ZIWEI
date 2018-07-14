package com.jyss.ziwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.BodyFecesMapper;
import com.jyss.ziwei.mapper.BodyFeelMapper;
import com.jyss.ziwei.mapper.StomachReactMapper;
import com.jyss.ziwei.service.BodyFeelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class BodyFeelServiceImpl implements BodyFeelService{

    @Autowired
    private StomachReactMapper stomachReactMapper;
    @Autowired
    private BodyFeelMapper bodyFeelMapper;
    @Autowired
    private BodyFecesMapper bodyFecesMapper;


    /**
     * 添加肠胃反应
     */
    @Override
    public ResponseResult insertStomachReact(Long uId, String cwData, String note) {
        StomachReact react = new StomachReact();
        react.setuId(uId);
        react.setCwData(cwData);
        react.setNote(note);
        react.setStatus(1);
        react.setCreateTime(new Date());
        react.setCreated(new Date());
        int count = stomachReactMapper.insert(react);
        if(count == 1){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","添加失败！");
    }


    /**
     * 查询肠胃反应
     */
    @Override
    public ResponseResult selectStomachReact(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> createds = stomachReactMapper.selectCreated(uId);
        List<Object> list = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        for (String created : createds) {
            Map<String, Object> map = new HashMap<>();
            List<StomachReact> reacts = stomachReactMapper.selectStomachReact(uId, created);
            /*try {
                Date date = dateFormat.parse(created);
            } catch (ParseException e) {
                return ResponseResult.error("-2","查询失败！");
            }*/
            map.put("date",created);
            map.put("reacts",reacts);
            list.add(map);
        }
        PageInfo<String> pageInfo = new PageInfo<>(createds);
        Page<Object> result = new Page<>();
        result.setTotal(pageInfo.getTotal());
        result.setNext(pageInfo.isHasNextPage());
        result.setRows(list);
        return ResponseResult.ok(result);
    }


    /**
     * 添加身体感受
     */
    @Override
    public ResponseResult insertBodyFeel(Long uId, String stData, String note) {
        BodyFeel bodyFeel = new BodyFeel();
        bodyFeel.setuId(uId);
        bodyFeel.setStData(stData);
        bodyFeel.setNote(note);
        bodyFeel.setStatus(1);
        bodyFeel.setCreateTime(new Date());
        bodyFeel.setCreated(new Date());
        int count = bodyFeelMapper.insert(bodyFeel);
        if(count == 1){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","添加失败！");
    }


    /**
     * 查询身体感受
     */
    @Override
    public ResponseResult selectBodyFeel(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> createds = bodyFeelMapper.selectCreated(uId);
        List<Object> list = new ArrayList<>();
        for (String created : createds) {
            Map<String, Object> map = new HashMap<>();
            List<BodyFeel> feels = bodyFeelMapper.selectBodyFeel(uId, created);
            map.put("date",created);
            map.put("feels",feels);
            list.add(map);
        }
        PageInfo<String> pageInfo = new PageInfo<>(createds);
        Page<Object> result = new Page<>();
        result.setTotal(pageInfo.getTotal());
        result.setNext(pageInfo.isHasNextPage());
        result.setRows(list);
        return ResponseResult.ok(result);
    }


    /**
     * 添加大便记录
     */
    @Override
    public ResponseResult insertBodyFeces(Long uId, BodyFeces bodyFeces) {
        bodyFeces.setuId(uId);
        bodyFeces.setStatus(1);
        bodyFeces.setCreateTime(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<BodyFeces> feces = bodyFecesMapper.selectBodyFeces(uId, dateFormat.format(new Date()));
        if(feces != null && feces.size() > 0){
            bodyFeces.setId(feces.get(0).getId());
            int count = bodyFecesMapper.updateByPrimaryKey(bodyFeces);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
        }else {
            int count = bodyFecesMapper.insert(bodyFeces);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
        }
        return ResponseResult.error("-2","添加失败！");
    }


    /**
     * 查询大便记录
     */
    @Override
    public ResponseResult selectBodyFeces(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BodyFeces> feces = bodyFecesMapper.selectBodyFeces(uId, null);
        PageInfo<BodyFeces> pageInfo = new PageInfo<>(feces);
        Page<BodyFeces> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


}
