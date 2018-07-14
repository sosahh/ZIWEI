package com.jyss.ziwei.action;


import com.jyss.ziwei.entity.BaseConfig;
import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.entity.ResponseEntity;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.service.ItemService;
import com.jyss.ziwei.service.MobileLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/item")
public class ItemAction {

    @Autowired
    private ItemService itemService;
    @Autowired
    private MobileLoginService mobileLoginService;


    /**
     * 我的消息
     */
    @RequestMapping("/message")
    @ResponseBody
    public ResponseResult selectUserMessage(@RequestParam("token")String token,
                                            @RequestParam(value = "page", required = true) Integer page,
                                            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uesrId = mobileLogin.getuId();
            ResponseResult result = itemService.selectUserMessage(uesrId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");

    }


    /**
     * 读取消息
     */
    @RequestMapping("/readMsg")
    @ResponseBody
    public ResponseResult updateUserMessage(@RequestParam("token")String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uesrId = mobileLogin.getuId();
            ResponseResult result = itemService.updateUserMessage(uesrId);
            return result;
        }
        return ResponseResult.error("1","token失效！");

    }


    /**
     * 用户协议
     */
    @RequestMapping("/agreement")
    @ResponseBody
    public ResponseResult selectBaseConfig(){
        List<BaseConfig> list = itemService.selectBaseConfig("sign.info",1);
        Map<String, Object> map = new HashMap<>();
        map.put("list",list);
        return ResponseResult.ok(map);

    }


    //////////////后台部分/////////////////
    /**
     * 修改用户协议
     */
    @RequestMapping("/upagree")
    @ResponseBody
    public ResponseEntity updateBaseConfig(@RequestParam("content")String content,@RequestParam("id")Integer id,
                                           @RequestParam("title")String title){
        if(StringUtils.isEmpty(id)){
            return ResponseEntity.op("-1","请选择一个更新！");
        }
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.setId(id);
        baseConfig.setContent(content);
        baseConfig.setTitle(title);
        baseConfig.setModifyTime(new Date());

        int count = itemService.updateByPrimaryKey(baseConfig);
        if(count == 1){
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("-2","更新失败！");

    }



}
