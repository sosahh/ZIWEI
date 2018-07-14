package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.entity.UserDevices;
import com.jyss.ziwei.mapper.MobileLoginMapper;
import com.jyss.ziwei.mapper.UserDevicesMapper;
import com.jyss.ziwei.service.UserDevicesService;
import com.jyss.ziwei.utils.CommTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserDevicesServiceImpl implements UserDevicesService{
    @Autowired
    private UserDevicesMapper udMapper;
    @Autowired
    private MobileLoginMapper loginMapper;

    @Override
    public int insert(UserDevices userDevices) {
        return udMapper.insert(userDevices);
    }

    @Override
    public int updateByPrimaryKey(UserDevices userDevices) {
        return udMapper.updateByPrimaryKey(userDevices);
    }


    //添加设备
    @Override
    public ResponseResult addDevice(String token, UserDevices ud) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        //添加设备
        ud.setuId(uId.intValue());
        ud.setStatus(1);
        int count = udMapper.insert(ud);
        if(count==1){
            m.put("msg","添加成功");
            return  ResponseResult.ok(m);
        }
        return ResponseResult.error("0", "添加失败！");
    }

    //修改设备
    @Override
    public ResponseResult upDevice(String token, UserDevices ud) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        //修改设备
        ud.setuId(uId.intValue());
        ud.setLastModifyTime(CommTool.getNowTimestamp());
        int count = udMapper.updateByPrimaryKey(ud);
        if(count==1){
            m.put("msg","修改成功");
            return   ResponseResult.ok(m);
        }
        return ResponseResult.error("0", "修改失败！");
    }

    //删除设备
    @Override
    public ResponseResult delDevice(String token, String udId) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        //删除设备
        int count = udMapper.deleteUserDevice(udId);
        if(count==1){
            m.put("msg","删除成功");
            return  ResponseResult.ok(m);
        }
        return ResponseResult.error("0", "删除失败！");
    }

    @Override
    public ResponseResult getMyDevice(String token) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        //获取设备
        List<UserDevices> list  = udMapper.getUserDevice(null,uId+"");
        Map<String, Object> map = new HashMap<>();
        map.put("list",list);
        return ResponseResult.ok(map);

    }
}

