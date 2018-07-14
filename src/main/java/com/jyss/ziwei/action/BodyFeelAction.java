package com.jyss.ziwei.action;

import com.jyss.ziwei.entity.BodyFeces;
import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.service.BodyFeelService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.PointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/body")
public class BodyFeelAction {

    @Autowired
    private MobileLoginService mobileLoginService;
    @Autowired
    private BodyFeelService bodyFeelService;
    @Autowired
    private PointsRecordService pointsRecordService;


    /**
     * 添加肠胃反应
     */
    @RequestMapping("/sinsert")
    @ResponseBody
    public ResponseResult insertStomachReact(@RequestParam("token")String token,@RequestParam("cwData")String cwData,
                                             @RequestParam("note")String note){
        if(StringUtils.isEmpty(cwData)){
            return ResponseResult.error("-1","肠胃反应不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            pointsRecordService.updateUserPointsRecord(uId,0,"肠胃反应录入",3);
            return bodyFeelService.insertStomachReact(uId, cwData, note);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询肠胃反应
     */
    @RequestMapping("/sslect")
    @ResponseBody
    public ResponseResult selectStomachReact(@RequestParam("token")String token,
                                             @RequestParam(value = "page", required = true) Integer page,
                                             @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyFeelService.selectStomachReact(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 添加身体感受
     */
    @RequestMapping("/binsert")
    @ResponseBody
    public ResponseResult insertBodyFeel(@RequestParam("token")String token,@RequestParam("stData")String stData,
                                             @RequestParam("note")String note){
        if(StringUtils.isEmpty(stData)){
            return ResponseResult.error("-1","身体感受不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            pointsRecordService.updateUserPointsRecord(uId,0,"身体感受录入",3);
            return bodyFeelService.insertBodyFeel(uId, stData, note);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询身体感受
     */
    @RequestMapping("/bslect")
    @ResponseBody
    public ResponseResult selectBodyFeel(@RequestParam("token")String token,
                                             @RequestParam(value = "page", required = true) Integer page,
                                             @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyFeelService.selectBodyFeel(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 添加大便记录
     */
    @RequestMapping("/finsert")
    @ResponseBody
    public ResponseResult insertBodyFeces(@RequestParam("token")String token,BodyFeces bodyFeces){
        if(bodyFeces.getCount() == null){
            return ResponseResult.error("-1","内容不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            pointsRecordService.updateUserPointsRecord(uId,0,"大便录入",3);
            return bodyFeelService.insertBodyFeces(uId,bodyFeces);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 查询大便记录
     */
    @RequestMapping("/fslect")
    @ResponseBody
    public ResponseResult selectBodyFeces(@RequestParam("token")String token,
                                          @RequestParam(value = "page", required = true) Integer page,
                                          @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyFeelService.selectBodyFeces(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }




}
