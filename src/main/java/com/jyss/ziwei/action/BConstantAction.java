package com.jyss.ziwei.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.BConstantService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.WebLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/constant")
public class BConstantAction {

    @Autowired
    private BConstantService bcService;
    @Autowired
    private WebLoginService webLoginService;
    @Autowired
    private MobileLoginService loginService;


    /**
     * 肠胃和身体感受常量     type：1=肠胃反应，2=身体感受
     */
    @RequestMapping("/bodyfeel")
    @ResponseBody
    public ResponseResult selectBodyFeel(@RequestParam("type")Integer type){
        Map<String, Object> map = new HashMap<>();
        if(type == 1){
            List<BConstant> list = bcService.selectBConstant("gastro_type", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }else if(type == 2){
            List<BConstant> list = bcService.selectBConstant("bodyfeel_type", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","查询失败！");
    }


    /**
     * 大便常量      type：1=大便颜色，2=大便气味，3=大便粘度，4=大便布里斯托分型
     */
    @RequestMapping("/feces")
    @ResponseBody
    public ResponseResult selectFeces(@RequestParam("type")Integer type){
        Map<String, Object> map = new HashMap<>();
        if(type == 1){
            List<BConstant> list = bcService.selectBConstant("feces_color", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }else if(type == 2){
            List<BConstant> list = bcService.selectBConstant("feces_smell", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }else if(type == 3){
            List<BConstant> list = bcService.selectBConstant("feces_viscosity", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }else if(type == 4){
            List<BConstant> list = bcService.selectBConstant("feces_score", null);
            map.put("list",list);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","查询失败！");
    }



    /**
     * OGTT闹钟时间
     */
    @RequestMapping("/getOGTT")
    @ResponseBody
    public ResponseResult getOGTT(@RequestParam("token")String token){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginService.findUserByToken(token);
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
        List<BConstant> bcList  = bcService.getConstant("1","clock_type","2",null,null);
        return ResponseResult.ok(bcList.get(0));

    }



    /////////////web///////////////
    @RequestMapping("/getList")
    @ResponseBody
    public ResponseEntity getBaseConfig(@RequestParam("token")String token,@RequestParam("page")int page,
                                        @RequestParam("limit")int limit){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginService.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ///业务操作
        ////2.分页查询
        PageHelper.startPage(page, limit);// 分页语句
        List<BConstant> bcList  = bcService.getConstant("1",null,null,null,null);
        PageInfo<BConstant> pageInfo = new PageInfo<BConstant>(bcList);
        return ResponseEntity.se(new Page<BConstant>(pageInfo));

    }

    @RequestMapping("/getSearch")
    @ResponseBody
    public ResponseEntity getSearch(@RequestParam("token")String token,@RequestParam("bzType")String bzType,
                                    @RequestParam("bzInfo")String bzInfo,@RequestParam("page")int page,
                                    @RequestParam("limit")int limit){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginService.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ///业务操作
        ////2.分页查询
        PageHelper.startPage(page, limit);// 分页语句
        List<BConstant> bcList  = bcService.getConstant("1",bzType,null,null,bzInfo);
        PageInfo<BConstant> pageInfo = new PageInfo<BConstant>(bcList);
        return ResponseEntity.se(new Page<BConstant>(pageInfo));

    }

    /**
     * 修改常量
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseEntity updateBaseConfig(@RequestParam("token")String token, BConstant bConstant){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginService.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
       ///业务操作
        int count = bcService.updateByPrimaryKey(bConstant);
        if(count == 1){
            return ResponseEntity.op("1","操作成功！");
        }
        return ResponseEntity.op("0","操作失败！");

    }

    /**
     * 新增常量
     */
    @RequestMapping("/insert")
    @ResponseBody
    public ResponseEntity insertBaseConfig(@RequestParam("token")String token, BConstant bConstant){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginService.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ///业务操作
        bConstant.setStatus(1);
        bConstant.setpId(0);
        int count = bcService.insert(bConstant);
        if(count == 1){
            return ResponseEntity.op("1","操作成功！");
        }
        return ResponseEntity.op("0","操作失败！");

    }

    /**
     * 删除常量
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEntity deleteBaseConfig(@RequestParam("token")String token, @RequestParam("cId")String cId){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginService.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ///业务操作
        int count = bcService.deleteConstant(cId);
        if(count == 1){
            return ResponseEntity.op("1","操作成功！");
        }
        return ResponseEntity.op("0","操作失败！");

    }




}