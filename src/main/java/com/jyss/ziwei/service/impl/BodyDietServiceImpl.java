package com.jyss.ziwei.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.BodyDietMapper;
import com.jyss.ziwei.mapper.BodyDrugMapper;
import com.jyss.ziwei.mapper.DrugMapper;
import com.jyss.ziwei.mapper.FoodMapper;
import com.jyss.ziwei.service.BodyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BodyDietServiceImpl implements BodyDietService {

    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private BodyDietMapper bodyDietMapper;
    @Autowired
    private DrugMapper drugMapper;
    @Autowired
    private BodyDrugMapper bodyDrugMapper;


    /**
     * 查询食物大卡
     */
    @Override
    public ResponseResult selectFood(String name, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Food> foods = foodMapper.selectFood(name);
        PageInfo<Food> pageInfo = new PageInfo<>(foods);
        Page<Food> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 添加饮食
     */
    @Override
    public ResponseResult insertBodyDiet(BodyDiet bodyDiet) {
        bodyDietMapper.insertBodyDiet(bodyDiet);
        return ResponseResult.ok(new HashMap<>());
    }


    /**
     * 添加多个饮食
     */
    @Override
    public ResponseResult insertPLBodyDiet(Long uId, String diets) {
        List<BodyDiet> bodyDiets = JSON.parseArray(diets, BodyDiet.class);
        for (BodyDiet bodyDiet : bodyDiets) {
            if(!StringUtils.isEmpty(bodyDiet.getPicture())){
                String picture = bodyDiet.getPicture();
                bodyDiet.setPicture(picture.substring(picture.lastIndexOf("uploadFood")));
            }
            bodyDiet.setuId(uId);
            bodyDiet.setCreated(bodyDiet.getCreateTime());
            bodyDietMapper.insertBodyDiet(bodyDiet);
        }
        return ResponseResult.ok(new HashMap<>());
    }


    /**
     * 查询用户饮食
     */
    @Override
    public ResponseResult selectBodyDiet(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> createds = bodyDietMapper.selectCreated(uId);
        List<Object> list = new ArrayList<>();
        for (String created : createds) {
            Map<String, Object> map = new HashMap<>();
            List<BodyDiet> diet1 = bodyDietMapper.selectBodyDiet(uId, "早餐", created);
            List<BodyDiet> diet2 = bodyDietMapper.selectBodyDiet(uId, "午餐", created);
            List<BodyDiet> diet3 = bodyDietMapper.selectBodyDiet(uId, "晚餐", created);
            map.put("date",created);
            map.put("diet1",diet1);
            map.put("diet2",diet2);
            map.put("diet3",diet3);
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
     * 查询药物
     */
    @Override
    public ResponseResult selectDrug(String name, Integer drugType, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> drugNames = drugMapper.selectDrugName(name, drugType);
        List<Object> list = new ArrayList<>();
        for (String drugName : drugNames) {
            Map<String, Object> map = new HashMap<>();
            List<Drug> drugs = drugMapper.selectDrug(drugName, name, drugType);

            map.put("drugName",drugName);
            map.put("drugs",drugs);
            list.add(map);
        }
        PageInfo<String> pageInfo = new PageInfo<>(drugNames);
        Page<Object> result = new Page<>();
        result.setTotal(pageInfo.getTotal());
        result.setNext(pageInfo.isHasNextPage());
        result.setRows(list);
        return ResponseResult.ok(result);
    }


    /**
     * 添加药物
     */
    @Override
    public ResponseResult insertBodyDrug(BodyDrug bodyDrug) {
        bodyDrugMapper.insert(bodyDrug);
        return ResponseResult.ok(new HashMap<>());
    }


    /**
     * 查询药物记录
     */
    @Override
    public ResponseResult selectBodyDrug(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> createds = bodyDrugMapper.selectCreated(uId);
        List<Object> list = new ArrayList<>();
        for (String created : createds) {
            Map<String, Object> map = new HashMap<>();
            List<BodyDrug> bodyDrugs = bodyDrugMapper.selectBodyDrug(uId, created);

            map.put("date",created);
            map.put("bodyDrugs",bodyDrugs);
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
     * 查询最近的饮食和药物记录
     */
    @Override
    public ResponseResult selectDrugAndDiet(Long uId) {


        return null;
    }


}
