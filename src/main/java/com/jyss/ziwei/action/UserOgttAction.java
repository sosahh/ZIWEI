package com.jyss.ziwei.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.BConstantService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.UserOgttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/OGTT")
public class UserOgttAction {

    @Autowired
    private MobileLoginService loginservice;
    @Autowired
    private UserOgttService uoService;
    @Autowired
    private BConstantService bConstantService;

    @RequestMapping("/getMyPhysicTest")
    @ResponseBody
    public ResponseResult getMyPhysicTest(@RequestParam("token")String token){
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("ogtt",new ArrayList());
        m.put("next",new ArrayList());
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginservice.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///业务操作
        List<UserOgtt>  plist = uoService.getUserOgtt("1",uId.toString(),null);
        if(plist!=null&&plist.size()>0){
            m.put("ogtt",plist.get(0));
        }
       //后续测量时间
        ///业务操作
        List<BConstant> bcList  = bConstantService.getConstant("1","clock_type","2",null,null);
        if(bcList!=null&&bcList.size()>0){
            m.put("next",bcList.get(0));
        }
        return ResponseResult.ok(m);


    }

    /**
     * 新增OGTT
     */
    @RequestMapping("/insert")
    @ResponseBody
    public ResponseResult insertPhysicTest(@RequestParam("token")String token, UserOgtt userOgtt){
        //1=验证登录
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginservice.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///业务操作
        List<UserOgtt>  plist = uoService.getUserOgtt("1",uId.toString(),null);
        if(plist!=null&&plist.size()>0){
          for(UserOgtt u:plist){
              ///保持唯一性
              uoService.deleteUserOgtt(u.getId().toString());
          }
        }
        ///插入
        userOgtt.setStatus(1);
        userOgtt.setuId(uId.intValue());
        int count = uoService.insert(userOgtt);
        if(count == 1){
            m.put("msg","操作成功");
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("0","操作失败！");

    }


}